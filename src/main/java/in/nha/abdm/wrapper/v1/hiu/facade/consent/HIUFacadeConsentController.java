/* (C) 2024 */
package in.nha.abdm.wrapper.v1.hiu.facade.consent;

import in.nha.abdm.wrapper.v1.common.exceptions.IllegalDataStateException;
import in.nha.abdm.wrapper.v1.common.responses.ErrorResponse;
import in.nha.abdm.wrapper.v1.common.responses.FacadeResponse;
import in.nha.abdm.wrapper.v1.hiu.hrp.consent.HIUConsentInterface;
import in.nha.abdm.wrapper.v1.hiu.hrp.consent.requests.InitConsentRequest;
import in.nha.abdm.wrapper.v1.hiu.hrp.consent.responses.ConsentStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(path = "/v1")
public class HIUFacadeConsentController {

  private final HIUConsentInterface hiuConsentInterface;

  @Autowired
  public HIUFacadeConsentController(HIUConsentInterface hiuConsentInterface) {
    this.hiuConsentInterface = hiuConsentInterface;
  }

  /**
   * Initiating the consent request to ABDM.
   *
   * @param initConsentRequest has abhaAddress and consent dateRange and basic requirement for
   *     consent.
   * @return status of request from ABDM.
   */
  @PostMapping({"/consent-init"})
  public ResponseEntity<FacadeResponse> initiateConsentRequest(
      @RequestBody InitConsentRequest initConsentRequest) {
    FacadeResponse facadeResponse = hiuConsentInterface.initiateConsentRequest(initConsentRequest);
    return new ResponseEntity<>(facadeResponse, facadeResponse.getHttpStatusCode());
  }

  /**
   * Getting the status of consent from hiu/status and consent/status api
   *
   * @param clientRequestId
   * @return list of consent artifacts with dateRange,expiry and hip details.
   */
  @GetMapping({"/consent-status/{requestId}"})
  public ResponseEntity<ConsentStatusResponse> consentRequestStatus(
      @PathVariable("requestId") String clientRequestId) throws IllegalDataStateException {
    ConsentStatusResponse consentStatusResponse =
        hiuConsentInterface.consentRequestStatus(clientRequestId);
    return new ResponseEntity<>(consentStatusResponse, consentStatusResponse.getHttpStatusCode());
  }

  @ExceptionHandler(IllegalDataStateException.class)
  private ResponseEntity<FacadeResponse> handleIllegalDataStateException(
      IllegalDataStateException ex) {
    return new ResponseEntity<>(
        FacadeResponse.builder()
            .error(ErrorResponse.builder().message(ex.getMessage()).build())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  private ResponseEntity<FacadeResponse> handleHttpClientException(HttpClientErrorException ex) {
    return new ResponseEntity<>(
        FacadeResponse.builder()
            .error(ErrorResponse.builder().message(ex.getMessage()).build())
            .build(),
        ex.getStatusCode());
  }
}
