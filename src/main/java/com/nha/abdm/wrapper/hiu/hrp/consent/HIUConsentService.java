/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.consent;

import com.nha.abdm.wrapper.common.RequestManager;
import com.nha.abdm.wrapper.common.responses.FacadeResponse;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.GatewayGenericResponse;
import com.nha.abdm.wrapper.hiu.hrp.consent.requests.ConsentStatusRequest;
import com.nha.abdm.wrapper.hiu.hrp.consent.requests.FetchConsentRequest;
import com.nha.abdm.wrapper.hiu.hrp.consent.requests.InitConsentRequest;
import com.nha.abdm.wrapper.hiu.hrp.consent.requests.OnNotifyRequest;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.Exceptions;

public class HIUConsentService implements HIUConsentInterface {

  private static final Logger log = LogManager.getLogger(HIUConsentService.class);

  @Value("${consentInitPath}")
  private String consentInitPath;

  @Value("${consentStatusPath}")
  private String consentStatusPath;

  @Value("${consentHiuOnNotifyPath}")
  private String consentHiuOnNotifyPath;

  @Value("${fetchConsentPath}")
  private String fetchConsentPath;

  private final RequestManager requestManager;

  @Autowired
  public HIUConsentService(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  public FacadeResponse initiateConsentRequest(InitConsentRequest initConsentRequest) {
    try {
      ResponseEntity<GatewayGenericResponse> response =
          requestManager.fetchResponseFromGateway(consentInitPath, initConsentRequest);
      if (Objects.nonNull(response.getBody())
          && Objects.nonNull(response.getBody().getErrorResponse())) {
        return FacadeResponse.builder()
            .error(response.getBody().getErrorResponse())
            .code(response.getStatusCode().value())
            .build();
      }
      return FacadeResponse.builder().code(response.getStatusCode().value()).build();
    } catch (Exception ex) {
      String error =
          "Exception while initiating consent request: "
              + ex.getMessage()
              + " unwrapped exception: "
              + Exceptions.unwrap(ex);
      log.debug(error);
      return FacadeResponse.builder()
          .message(error)
          .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .build();
    }
  }

  @Override
  public FacadeResponse consentRequestStatus(ConsentStatusRequest consentStatusRequest) {
    try {
      ResponseEntity<GatewayGenericResponse> response =
          requestManager.fetchResponseFromGateway(consentStatusPath, consentStatusRequest);
      if (Objects.nonNull(response.getBody())
          && Objects.nonNull(response.getBody().getErrorResponse())) {
        return FacadeResponse.builder()
            .error(response.getBody().getErrorResponse())
            .code(response.getStatusCode().value())
            .build();
      }
      return FacadeResponse.builder().code(response.getStatusCode().value()).build();
    } catch (Exception ex) {
      String error =
          "Exception while fetching consent status: "
              + ex.getMessage()
              + " unwrapped exception: "
              + Exceptions.unwrap(ex);
      log.debug(error);
      return FacadeResponse.builder()
          .message(error)
          .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .build();
    }
  }

  @Override
  public FacadeResponse hiuOnNotify(OnNotifyRequest onNotifyRequest) {
    try {
      ResponseEntity<GatewayGenericResponse> response =
          requestManager.fetchResponseFromGateway(consentHiuOnNotifyPath, onNotifyRequest);
      if (Objects.nonNull(response.getBody())
          && Objects.nonNull(response.getBody().getErrorResponse())) {
        return FacadeResponse.builder()
            .error(response.getBody().getErrorResponse())
            .code(response.getStatusCode().value())
            .build();
      }
      return FacadeResponse.builder().code(response.getStatusCode().value()).build();
    } catch (Exception ex) {
      String error =
          "Exception while executing on notify: "
              + ex.getMessage()
              + " unwrapped exception: "
              + Exceptions.unwrap(ex);
      log.debug(error);
      return FacadeResponse.builder()
          .message(error)
          .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .build();
    }
  }

  @Override
  public FacadeResponse fetchConsent(FetchConsentRequest fetchConsentRequest) {
    try {
      ResponseEntity<GatewayGenericResponse> response =
          requestManager.fetchResponseFromGateway(fetchConsentPath, fetchConsentRequest);
      if (Objects.nonNull(response.getBody())
          && Objects.nonNull(response.getBody().getErrorResponse())) {
        return FacadeResponse.builder()
            .error(response.getBody().getErrorResponse())
            .code(response.getStatusCode().value())
            .build();
      }
      return FacadeResponse.builder().code(response.getStatusCode().value()).build();
    } catch (Exception ex) {
      String error =
          "Exception while fetching consent: "
              + ex.getMessage()
              + " unwrapped exception: "
              + Exceptions.unwrap(ex);
      log.debug(error);
      return FacadeResponse.builder()
          .message(error)
          .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .build();
    }
  }
}
