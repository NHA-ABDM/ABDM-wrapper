/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.consent.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIPNotifyRequest {
  private String requestId;
  private String timestamp;
  private HIPNotification HIPNotification;
}
