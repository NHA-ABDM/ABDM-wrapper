/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.link.responses.helpers;

import com.nha.abdm.wrapper.hiu.hrp.discovery.responses.helpers.CareContextRequest;
import java.util.List;

public class PatientResponse {
  private String referenceNumber;
  private String display;
  private List<CareContextRequest> careContextsResponses;
}
