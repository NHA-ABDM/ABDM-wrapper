/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery;

import com.nha.abdm.wrapper.common.RequestManager;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.DiscoverResponse;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HIUFacadeControllerService implements HIUFacadeControllerInterface {
  @Value("${hiuDiscoverPath}")
  public String hiuDiscoverPath;

  private final RequestManager requestManager;

  //  @Value("${hiuInitPath}")
  //  public String hiuInitPath;
  //
  //  @Value("${hiuLinkConfirmPath}")
  //  public String hiuLinkConfirmPath;

  private static final Logger log = LogManager.getLogger(HIUFacadeControllerService.class);

  public HIUFacadeControllerService(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  public ResponseEntity<DiscoverResponse> initiateDiscover(
      HIUDiscoverRequest hiuDiscoverRequest, String xAuthToken) {
    log.info(hiuDiscoverRequest.toString());
    log.info(xAuthToken);
    if (Objects.nonNull(hiuDiscoverRequest)
        && Objects.nonNull(hiuDiscoverRequest.getHip())
        && Objects.nonNull(hiuDiscoverRequest.getRequestId())
        && Objects.nonNull(xAuthToken)) {
      ResponseEntity<DiscoverResponse> response =
          requestManager.bhaijan(hiuDiscoverPath, hiuDiscoverRequest, xAuthToken);

      log.info(response.getBody().toString());
      return response;
    }
    return null;
  }

  //  @Override
  //  public ResponseEntity<InitResponse> initiateLinkInit(
  //      HIUInitRequest hiuInitRequest, String xAuthToken) {
  //
  //    if (Objects.nonNull(hiuInitRequest)
  //        && Objects.nonNull(hiuInitRequest.getRequestId())
  //        && Objects.nonNull(hiuInitRequest.getTransactionId())
  //        && Objects.nonNull(hiuInitRequest.getPatient())
  //        && Objects.nonNull(xAuthToken)) {
  //      try {
  //        ResponseEntity<InitResponse> response =
  //            requestManager.bhaijan2(hiuInitPath, hiuInitRequest, xAuthToken);
  //        return response;
  //
  //      } catch (Exception e) {
  //        throw new RuntimeException("unable to make post request from linkint " + e);
  //      }
  //    }
  //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InitResponse());
  //  }
  //
  //  @Override
  //  public ResponseEntity<ConfirmResponse> initiateLinkConfirm(
  //      HIUConfirmRequest hiuConfirmRequest, String xAuthToken) {
  //
  //    if (Objects.nonNull(hiuConfirmRequest)
  //        && Objects.nonNull(hiuConfirmRequest.getReferenceNumber())
  //        && Objects.nonNull(hiuConfirmRequest.getToken())
  //        && Objects.nonNull(xAuthToken)) {
  //      try {
  //        ResponseEntity<ConfirmResponse> response =
  //            requestManager.bhaijan3(hiuLinkConfirmPath, hiuConfirmRequest, xAuthToken);
  //        return response;
  //
  //      } catch (Exception e) {
  //        throw new RuntimeException("unable to make post request from linkconfirm " + e);
  //      }
  //    }
  //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ConfirmResponse());
  //  }
}
