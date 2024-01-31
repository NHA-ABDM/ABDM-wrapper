/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.link.userInitiated.requests;

import com.nha.abdm.wrapper.common.models.RespRequest;
import com.nha.abdm.wrapper.common.responses.ErrorResponse;
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
  private RespRequest resp;
  private ErrorResponse error;
}
