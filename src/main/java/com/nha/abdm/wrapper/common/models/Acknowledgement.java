/* (C) 2024 */
package com.nha.abdm.wrapper.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acknowledgement {
  private String status;
  private String consentId;
}
