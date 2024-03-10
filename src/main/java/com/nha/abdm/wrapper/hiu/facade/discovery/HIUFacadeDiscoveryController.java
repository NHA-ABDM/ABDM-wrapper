/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.facade.discovery;

import com.nha.abdm.wrapper.hiu.hrp.discovery.HIUFacadeControllerInterface;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUConfirmRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUInitRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.InitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/care-contexts")
public class HIUFacadeDiscoveryController {
  @Autowired HIUFacadeControllerInterface hiuFacadeControllerInterface;

  @PostMapping("/discover")
  public ResponseEntity<DiscoverResponse> hiuDiscoverResponse(
      @RequestHeader(value = "X-AUTH-TOKEN") String xAuthToken,
      @RequestBody HIUDiscoverRequest discoverRequest) {
    return hiuFacadeControllerInterface.initiateDiscover(discoverRequest, xAuthToken);
  }

  @PostMapping("/link/init")
  public ResponseEntity<InitResponse> hiuInitResponse(
      @RequestHeader(value = "X-AUTH-TOKEN") String xAuthToken, HIUInitRequest hiuInitRequest) {
    return hiuFacadeControllerInterface.initiateLinkInit(hiuInitRequest, xAuthToken);
  }

  @PostMapping("/link/confirm")
  public ResponseEntity<ConfirmResponse> hiuConfirmResponse(
      @RequestHeader(value = "X-AUTH-TOKEN") String xAuthToken,
      HIUConfirmRequest hiuConfirmRequest) {
    return hiuFacadeControllerInterface.initiateLinkConfirm(hiuConfirmRequest, xAuthToken);
  }
}
