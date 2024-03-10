/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.facade.discovery;

import com.nha.abdm.wrapper.hiu.hrp.discovery.HIUFacadeDiscoverControllerInterface;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/care-contexts")
public class HIUFacadeDiscoveryController {
  @Autowired HIUFacadeDiscoverControllerInterface hiuFacadeDiscoverControllerInterface;

  @PostMapping("/discover")
  public ResponseEntity<Object> hiuDiscoverResponse(
      @RequestHeader(value = "X-AUTH-TOKEN") String xAuthToken,
      @RequestBody HIUDiscoverRequest discoverRequest) {
    return hiuFacadeDiscoverControllerInterface.initiateDiscover(discoverRequest, xAuthToken);
  }
}
