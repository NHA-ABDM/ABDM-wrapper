/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.consent.requests;

import com.nha.abdm.wrapper.common.models.ConsentDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIPNotification {
  private String status;
  private String consentId;
  private ConsentDetail consentDetail;
}
