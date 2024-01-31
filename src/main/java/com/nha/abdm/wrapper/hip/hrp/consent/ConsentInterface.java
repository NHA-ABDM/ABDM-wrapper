/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.consent;

import com.nha.abdm.wrapper.hip.hrp.consent.requests.HIPOnNotifyRequest;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.GatewayGenericResponse;
import org.springframework.http.ResponseEntity;

public interface ConsentInterface {
  ResponseEntity<GatewayGenericResponse> hipOnNotify(HIPOnNotifyRequest hip);
}
