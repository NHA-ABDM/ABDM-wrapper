/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.link.requests;

import com.nha.abdm.wrapper.common.ErrorResponse;
import com.nha.abdm.wrapper.hip.hrp.discover.requests.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnConfirmRequest {

  private String requestId;
  private String timestamp;
  private OnConfirmPatient patient;
  private Response resp;
  private ErrorResponse error;
}