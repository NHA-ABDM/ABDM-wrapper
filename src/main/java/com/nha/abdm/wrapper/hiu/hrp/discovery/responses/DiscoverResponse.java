/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery.responses;

import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.helpers.HIUPatient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscoverResponse {
  public String transactionId;
  public HIUPatient patient;
  public String createdAt;
}
