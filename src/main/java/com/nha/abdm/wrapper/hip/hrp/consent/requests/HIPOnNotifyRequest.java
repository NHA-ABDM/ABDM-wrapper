/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.consent.requests;

import com.nha.abdm.wrapper.common.models.Acknowledgement;
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
public class HIPOnNotifyRequest {
  private String requestId;
  private String timestamp;
  private Acknowledgement acknowledgement;
  private ErrorResponse error;
  private RespRequest resp;
}
