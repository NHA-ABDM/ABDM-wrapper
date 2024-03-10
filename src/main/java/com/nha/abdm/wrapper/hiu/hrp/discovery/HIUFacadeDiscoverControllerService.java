/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery;

import com.nha.abdm.wrapper.common.RequestManager;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.DiscoverResponse;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HIUFacadeDiscoverControllerService implements HIUFacadeDiscoverControllerInterface {
  @Value("${hiuDiscoverPath}")
  public String hiuDiscoverPath;

  private final RequestManager requestManager;

  private static final Logger log = LogManager.getLogger(HIUFacadeDiscoverControllerService.class);

  public HIUFacadeDiscoverControllerService(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  @Override
  public ResponseEntity<Object> initiateDiscover(
      HIUDiscoverRequest hiuDiscoverRequest, String xAuthToken) {
    log.info(hiuDiscoverRequest.toString());
    if (Objects.nonNull(hiuDiscoverRequest)
        && Objects.nonNull(hiuDiscoverRequest.getHip())
        && Objects.nonNull(hiuDiscoverRequest.getRequestId())
        && Objects.nonNull(xAuthToken)) {
      try {
        ResponseEntity<Object> response =
            requestManager.fetchResponseForPHR(hiuDiscoverPath, hiuDiscoverRequest, xAuthToken, "");

        log.info(response.getBody().toString());
        return response;

      } catch (Exception e) {
        throw new RuntimeException("unable to make post request from discover " + e);
      }
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DiscoverResponse());
  }
}
