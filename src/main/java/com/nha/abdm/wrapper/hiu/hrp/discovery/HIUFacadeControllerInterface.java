/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery;

import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUConfirmRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUInitRequest;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.InitResponse;
import org.springframework.http.ResponseEntity;

public interface HIUFacadeControllerInterface {
  ResponseEntity<DiscoverResponse> initiateDiscover(
      HIUDiscoverRequest hiuDiscoverRequest, String xAuthToken);

  ResponseEntity<InitResponse> initiateLinkInit(HIUInitRequest hiuInitRequest, String xAuthToken);

  ResponseEntity<ConfirmResponse> initiateLinkConfirm(
      HIUConfirmRequest hiuConfirmRequest, String xAuthToken);
}
