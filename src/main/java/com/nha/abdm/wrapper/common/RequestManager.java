/* (C) 2024 */
package com.nha.abdm.wrapper.common;

import com.nha.abdm.wrapper.common.requests.SessionManager;
import com.nha.abdm.wrapper.common.responses.GenericResponse;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.DiscoverResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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

  public ResponseEntity<DiscoverResponse> bhaijan(
      String uri, HIUDiscoverRequest request, String xAuthToken) {
    return hiuWebClient
        .post()
        .uri(uri)
        .headers(headers -> headers.setBasicAuth(xAuthToken))
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .toEntity(DiscoverResponse.class)
        .block();
  }
}
