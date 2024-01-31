/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.consent;

import com.nha.abdm.wrapper.common.RequestManager;
import com.nha.abdm.wrapper.hip.hrp.consent.requests.HIPOnNotifyRequest;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.GatewayGenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

public class ConsentService implements ConsentInterface {

  @Autowired RequestManager requestManager;

  @Value("${consentHipOnNotifyPath}")
  private String consentHipOnNotifyPath;

  @Override
  public ResponseEntity<GatewayGenericResponse> hipOnNotify(HIPOnNotifyRequest hipOnNotifyRequest) {
    return requestManager.fetchResponseFromGateway(consentHipOnNotifyPath, hipOnNotifyRequest);
  }
}
