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
public class OnInitRequest {
  private String requestId;
  private String timestamp;
  private String transactionId;
  private OnInitLink link;
  private RespRequest resp;
  private ErrorResponse error;
}
