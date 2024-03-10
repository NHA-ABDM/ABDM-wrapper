/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery.requests;

import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.helpers.HIPId;
import com.nha.abdm.wrapper.hiu.hrp.discovery.requests.helpers.HIUPatientUnverifiedIdentifiers;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIUDiscoverRequest {
  public HIPId hip;
  public String requestId;
  public List<HIUPatientUnverifiedIdentifiers> unverifiedIdentifiers;
}
