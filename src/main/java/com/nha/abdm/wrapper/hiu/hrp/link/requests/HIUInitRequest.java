/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.link.requests;

import com.nha.abdm.wrapper.hiu.hrp.link.requests.helpers.PatientWithReferenceNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIUInitRequest {
  public String requestId;
  public String transactionId;
  public PatientWithReferenceNumber patient;
}
