/* (C) 2024 */
package com.nha.abdm.wrapper.common;

import com.nha.abdm.wrapper.common.requests.SessionManager;
import com.nha.abdm.wrapper.common.responses.GenericResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RequestManager {
  private static final Logger log = LogManager.getLogger(RequestManager.class);

  private final WebClient webClient;
  private final WebClient hiuWebClient;
  private final SessionManager sessionManager;

  @Autowired
  public RequestManager(
      @Value("${gatewayBaseUrl}") final String gatewayBaseUrl, SessionManager sessionManager) {
    this.sessionManager = sessionManager;
    webClient =
        WebClient.builder()
            .baseUrl(gatewayBaseUrl)
            .defaultHeaders(
                httpHeaders -> httpHeaders.addAll(sessionManager.setGatewayRequestHeaders()))
            .build();
    hiuWebClient = WebClient.builder().build();
  }

  // Initializing headers every time to avoid setting the old headers/session token and getting
  // unauthorised error from gateway.
  public <T> ResponseEntity<GenericResponse> fetchResponseFromGateway(String uri, T request) {
    return webClient
        .post()
        .uri(uri)
        .headers(httpHeaders -> httpHeaders.addAll(sessionManager.setGatewayRequestHeaders()))
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .toEntity(GenericResponse.class)
        .block();
  }

  public ResponseEntity<Object> fetchResponseForPHR(
      String uri, Object request, String xAuthToken, String path) {
    return hiuWebClient
        .post()
        .uri(uri + path)
        .headers(headers -> headers.add("X-AUTH-TOKEN", xAuthToken))
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .toEntity(Object.class)
        .block();
  }
}
