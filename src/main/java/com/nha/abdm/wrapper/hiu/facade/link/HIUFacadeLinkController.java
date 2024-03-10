/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.facade.link;

import com.nha.abdm.wrapper.hiu.hrp.link.HIUFacadeLinkControllerInterface;
import com.nha.abdm.wrapper.hiu.hrp.link.requests.HIUConfirmRequest;
import com.nha.abdm.wrapper.hiu.hrp.link.requests.HIUInitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/care-contexts/link")
public class HIUFacadeLinkController {
  @Autowired HIUFacadeLinkControllerInterface hiuFacadeLinkControllerInterface;

  @PostMapping("/init")
  public ResponseEntity<Object> hiuInitResponse(
      @RequestHeader(value = "X-AUTH-TOKEN") String xAuthToken,
      @RequestBody HIUInitRequest hiuInitRequest) {
    return hiuFacadeLinkControllerInterface.initiateLinkInit(hiuInitRequest, xAuthToken);
  }

  @PostMapping("/confirm")
  public ResponseEntity<Object> hiuConfirmResponse(
      @RequestHeader(value = "X-AUTH-TOKEN") String xAuthToken,
      @RequestBody HIUConfirmRequest hiuConfirmRequest) {
    return hiuFacadeLinkControllerInterface.initiateLinkConfirm(hiuConfirmRequest, xAuthToken);
  }
}
