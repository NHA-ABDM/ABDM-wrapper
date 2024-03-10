/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery.responses;

import com.nha.abdm.wrapper.common.responses.ErrorResponse;
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
  private static final long serialVersionUID = 165269402517398406L;
  public String transactionId;
  public HIUPatient patient;
  public String createdAt;
  public ErrorResponse error;
}
