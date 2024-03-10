/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery;

import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.DiscoverResponse;
import org.springframework.http.ResponseEntity;

public interface HIUFacadeControllerInterface {

  ResponseEntity<DiscoverResponse> initiateDiscover(
      HIUDiscoverRequest hiuDiscoverRequest, String xAuthToken);

  //  ResponseEntity<InitResponse> initiateLinkInit(HIUInitRequest hiuInitRequest, String
  // xAuthToken);
  //
  //  ResponseEntity<ConfirmResponse> initiateLinkConfirm(
  //      HIUConfirmRequest hiuConfirmRequest, String xAuthToken);
}
