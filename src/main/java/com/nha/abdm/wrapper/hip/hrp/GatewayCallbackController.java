/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp;

import com.nha.abdm.wrapper.common.GatewayConstants;
import com.nha.abdm.wrapper.common.Utils;
import com.nha.abdm.wrapper.common.exceptions.IllegalDataStateException;
import com.nha.abdm.wrapper.common.models.Acknowledgement;
import com.nha.abdm.wrapper.common.responses.ErrorResponse;
import com.nha.abdm.wrapper.common.responses.FacadeResponse;
import com.nha.abdm.wrapper.common.responses.GatewayCallbackResponse;
import com.nha.abdm.wrapper.hip.hrp.consent.ConsentService;
import com.nha.abdm.wrapper.hip.hrp.consent.requests.HIPNotifyRequest;
import com.nha.abdm.wrapper.hip.hrp.consent.requests.HIPOnNotifyRequest;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.repositories.LogsRepo;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.services.PatientService;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.services.RequestLogService;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.tables.RequestLog;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.tables.helpers.RequestStatus;
import com.nha.abdm.wrapper.hip.hrp.discover.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.LinkOnAddCareContextsResponse;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.LinkOnConfirmResponse;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.LinkOnInitResponse;
import com.nha.abdm.wrapper.hip.hrp.link.userInitiated.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hip.hrp.link.userInitiated.responses.InitResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class GatewayCallbackController {

  @Autowired WorkflowManager workflowManager;
  @Autowired RequestLogService requestLogService;
  @Autowired PatientService patientService;
  @Autowired ConsentService consentService;
  @Autowired LogsRepo logsRepo;

  private static final Logger log = LogManager.getLogger(GatewayCallbackController.class);

  /**
   * discovery
   *
   * <p>Routing to workFlowManager for using service interface.
   *
   * @param discoverResponse response body with demographic details and abhaAddress of patient.
   */
  @PostMapping("/v0.5/care-contexts/discover")
  public void discoverCall(@RequestBody DiscoverResponse discoverResponse) {
    if (discoverResponse != null && discoverResponse.getError() == null) {
      log.info("/v0.5/care-contexts/discover :" + discoverResponse);
      workflowManager.initiateOnDiscover(discoverResponse);
    } else {
      log.error("/v0.5/care-contexts/discover :" + discoverResponse.getError().getMessage());
    }
  }

  /**
   * userInitiatedLinking
   *
   * <p>Routing to workFlowManager for using service interface.
   *
   * @param initResponse Response from ABDM gateway which has careContexts.
   */
  @PostMapping("/v0.5/links/link/init")
  public void initCall(@RequestBody InitResponse initResponse) {
    if (initResponse != null && initResponse.getError() == null) {
      log.info("/v0.5/links/link/init :" + initResponse);
      workflowManager.initiateOnInit(initResponse);
    } else {
      log.error("/v0.5/links/link/init :" + initResponse.getError().getMessage());
    }
  }

  /**
   * userInitiatedLinking
   *
   * <p>Routing to workFlowManager for using service interface.
   *
   * @param confirmResponse Response from ABDM gateway which has OTP sent by facility to user for
   *     authentication.
   */
  @PostMapping("/v0.5/links/link/confirm")
  public void confirmCall(@RequestBody ConfirmResponse confirmResponse) {
    if (confirmResponse != null && confirmResponse.getError() == null) {
      log.info("/v0.5/links/link/confirm : " + confirmResponse);
      workflowManager.initiateOnConfirmCall(confirmResponse);
    } else {
      log.error("/v0.5/links/link/confirm : " + confirmResponse.getError().getMessage());
    }
  }

  /**
   * hipInitiatedLinking
   *
   * <p>Routing to workFlowManager for using service interface.
   *
   * @param linkOnInitResponse Response from ABDM gateway after auth/init which has transactionId.
   */
  @PostMapping({"/v0.5/users/auth/on-init"})
  public ResponseEntity<GatewayCallbackResponse> onAuthInitCall(
      @RequestBody LinkOnInitResponse linkOnInitResponse) throws IllegalDataStateException {
    if (linkOnInitResponse != null && linkOnInitResponse.getError() != null) {
      updateRequestError(
          linkOnInitResponse.getResp().getRequestId(),
          "onAuthInitCall",
          linkOnInitResponse.getError().getMessage(),
          RequestStatus.AUTH_ON_INIT_ERROR);
    } else if (linkOnInitResponse != null) {
      log.debug(linkOnInitResponse.toString());
      this.workflowManager.initiateAuthConfirm(linkOnInitResponse);
    } else {
      String error = "Got Error in OnInitRequest callback: gateway response was null";
      return new ResponseEntity<>(
          GatewayCallbackResponse.builder()
              .error(
                  ErrorResponse.builder().code(GatewayConstants.ERROR_CODE).message(error).build())
              .build(),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * hipInitiatedLinking
   *
   * <p>Routing to workFlowManager for using service interface
   *
   * @param linkOnConfirmResponse Response from ABDM gateway after successful auth/confirm which has
   *     linkToken to link careContext.
   */
  @PostMapping({"/v0.5/users/auth/on-confirm"})
  public ResponseEntity<GatewayCallbackResponse> onAuthConfirmCall(
      @RequestBody LinkOnConfirmResponse linkOnConfirmResponse) throws IllegalDataStateException {
    if (linkOnConfirmResponse != null && linkOnConfirmResponse.getError() != null) {
      updateRequestError(
          linkOnConfirmResponse.getResp().getRequestId(),
          "onAuthConfirmCall",
          linkOnConfirmResponse.getError().getMessage(),
          RequestStatus.AUTH_ON_CONFIRM_ERROR);
    } else if (linkOnConfirmResponse != null) {
      log.debug(linkOnConfirmResponse.toString());
      this.workflowManager.addCareContext(linkOnConfirmResponse);
    } else {
      String error = "Got Error in onAuthConfirmCall callback: gateway response was null";
      return new ResponseEntity<>(
          GatewayCallbackResponse.builder()
              .error(
                  ErrorResponse.builder().code(GatewayConstants.ERROR_CODE).message(error).build())
              .build(),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * hipInitiatedLinking
   *
   * <p>Gets the status of the linking of careContexts with abhaAddress.
   *
   * @param linkOnAddCareContextsResponse Response from ABDM gateway which has acknowledgement of
   *     linking.
   */
  @PostMapping({"/v0.5/links/link/on-add-contexts"})
  public ResponseEntity<GatewayCallbackResponse> onAddCareContext(
      @RequestBody LinkOnAddCareContextsResponse linkOnAddCareContextsResponse)
      throws IllegalDataStateException {
    if (linkOnAddCareContextsResponse != null && linkOnAddCareContextsResponse.getError() != null) {
      updateRequestError(
          linkOnAddCareContextsResponse.getResp().getRequestId(),
          "onAddCareContext",
          linkOnAddCareContextsResponse.getError().getMessage(),
          RequestStatus.AUTH_ON_ADD_CARE_CONTEXT_ERROR);
    } else if (linkOnAddCareContextsResponse != null) {
      log.debug(linkOnAddCareContextsResponse.toString());
      requestLogService.setHipOnAddCareContextResponse(linkOnAddCareContextsResponse);
    } else {
      String error = "Got Error in onAddCareContext callback: gateway response was null";
      return new ResponseEntity<>(
          GatewayCallbackResponse.builder()
              .error(
                  ErrorResponse.builder().code(GatewayConstants.ERROR_CODE).message(error).build())
              .build(),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @PostMapping({"/v0.5/consents/hip/notify"})
  public ResponseEntity<GatewayCallbackResponse> hipNotify(
      @RequestBody HIPNotifyRequest hipNotifyRequest) {
    if (hipNotifyRequest != null
        && hipNotifyRequest.getHIPNotification() != null
        && hipNotifyRequest.getHIPNotification().getConsentDetail() != null
        && hipNotifyRequest.getHIPNotification().getConsentDetail().getPatient() != null) {
      patientService.addConsentArtefacts(hipNotifyRequest);
      HIPOnNotifyRequest hipOnNotifyRequest =
          HIPOnNotifyRequest.builder()
              .requestId(hipNotifyRequest.getRequestId())
              .timestamp(Utils.getCurrentTimeStamp())
              .acknowledgement(
                  Acknowledgement.builder()
                      .consentId(hipNotifyRequest.getHIPNotification().getConsentId())
                      .status(HttpStatus.OK.name())
                      .build())
              .build();
      consentService.hipOnNotify(hipOnNotifyRequest);
    } else {
      String error = "Got Error in onAddCareContext callback: gateway response was null";
      return new ResponseEntity<>(
          GatewayCallbackResponse.builder()
              .error(
                  ErrorResponse.builder().code(GatewayConstants.ERROR_CODE).message(error).build())
              .build(),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  private void updateRequestError(
      String requestId, String methodName, String errorMessage, RequestStatus requestStatus)
      throws IllegalDataStateException {
    RequestLog requestLog = logsRepo.findByGatewayRequestId(requestId);
    if (requestLog == null) {
      String error = "Illegal State - Request Id not found in database: " + requestId;
      log.error(error);
      throw new IllegalDataStateException(error);
    }
    String error = String.format("Got Error in %s callback: %s", methodName, errorMessage);
    log.error(error);
    requestLogService.updateError(requestLog, error, requestStatus);
  }

  @ExceptionHandler(Exception.class)
  private ResponseEntity<FacadeResponse> handleException(Exception ex) {
    return new ResponseEntity<>(
        FacadeResponse.builder()
            .message(ex.getMessage())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
