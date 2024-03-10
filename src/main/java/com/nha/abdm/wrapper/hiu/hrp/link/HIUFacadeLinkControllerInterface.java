/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.link;

import com.nha.abdm.wrapper.hiu.hrp.link.requests.HIUConfirmRequest;
import com.nha.abdm.wrapper.hiu.hrp.link.requests.HIUInitRequest;
import org.springframework.http.ResponseEntity;

public interface HIUFacadeLinkControllerInterface {
  ResponseEntity<Object> initiateLinkInit(HIUInitRequest hiuInitRequest, String xAuthToken);

  ResponseEntity<Object> initiateLinkConfirm(
      HIUConfirmRequest hiuConfirmRequest, String xAuthToken);
}
