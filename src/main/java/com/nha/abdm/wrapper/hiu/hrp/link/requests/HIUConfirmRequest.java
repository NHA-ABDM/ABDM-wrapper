/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.link.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIUConfirmRequest {
  public String token;
  public String referenceNumber;
}
