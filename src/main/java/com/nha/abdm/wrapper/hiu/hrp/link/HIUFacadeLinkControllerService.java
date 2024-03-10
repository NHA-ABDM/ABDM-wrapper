/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.link;

import com.nha.abdm.wrapper.common.RequestManager;
import com.nha.abdm.wrapper.hiu.hrp.link.requests.HIUConfirmRequest;
import com.nha.abdm.wrapper.hiu.hrp.link.requests.HIUInitRequest;
import com.nha.abdm.wrapper.hiu.hrp.link.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hiu.hrp.link.responses.InitResponse;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HIUFacadeLinkControllerService implements HIUFacadeLinkControllerInterface {
  private static final Logger log = LogManager.getLogger(HIUFacadeLinkControllerInterface.class);

  private final RequestManager requestManager;

  @Value("${hiuInitPath}")
  public String hiuInitPath;

  @Value("${hiuLinkConfirmPath}")
  public String hiuLinkConfirmPath;

  public HIUFacadeLinkControllerService(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  public ResponseEntity<Object> initiateLinkInit(HIUInitRequest hiuInitRequest, String xAuthToken) {
    log.info(hiuInitRequest.toString());

    if (Objects.nonNull(hiuInitRequest)
        && Objects.nonNull(hiuInitRequest.getRequestId())
        && Objects.nonNull(hiuInitRequest.getTransactionId())
        && Objects.nonNull(hiuInitRequest.getPatient())
        && Objects.nonNull(xAuthToken)) {
      try {
        ResponseEntity<Object> response =
            requestManager.fetchResponseForPHR(hiuInitPath, hiuInitRequest, xAuthToken, "");
        return response;

      } catch (Exception e) {
        throw new RuntimeException("unable to make post request from linkint " + e);
      }
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InitResponse());
  }

  @Override
  public ResponseEntity<Object> initiateLinkConfirm(
      HIUConfirmRequest hiuConfirmRequest, String xAuthToken) {
    log.info(hiuConfirmRequest.toString());
    if (Objects.nonNull(hiuConfirmRequest)
        && Objects.nonNull(hiuConfirmRequest.getReferenceNumber())
        && Objects.nonNull(hiuConfirmRequest.getToken())
        && Objects.nonNull(xAuthToken)) {
      try {
        ResponseEntity<Object> response =
            requestManager.fetchResponseForPHR(
                hiuLinkConfirmPath,
                hiuConfirmRequest,
                xAuthToken,
                "/" + hiuConfirmRequest.getReferenceNumber());
        return response;

      } catch (Exception e) {
        throw new RuntimeException("unable to make post request from linkconfirm " + e);
      }
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ConfirmResponse());
  }
}
