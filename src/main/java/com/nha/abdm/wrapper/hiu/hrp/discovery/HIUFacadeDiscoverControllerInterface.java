/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery;

import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.HIUDiscoverRequest;
import org.springframework.http.ResponseEntity;

public interface HIUFacadeDiscoverControllerInterface {

  ResponseEntity<Object> initiateDiscover(HIUDiscoverRequest hiuDiscoverRequest, String xAuthToken);
}
