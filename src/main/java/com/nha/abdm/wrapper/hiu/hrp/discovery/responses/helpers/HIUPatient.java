/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.discovery.responses.helpers;

import com.nha.abdm.wrapper.common.models.CareContext;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HIUPatient {
  public String referenceNumber;
  public String display;
  public List<CareContext> careContexts;
}
