/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.discover.requests;

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
public class OnDiscoverErrorRequest {

  private String requestId;
  private String timestamp;
  private String transactionId;
  private ErrorResponse error;
  private RespRequest resp;
}
