/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery.requests.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIUPatientUnverifiedIdentifiers {
  public String type;
  public String value;
}
