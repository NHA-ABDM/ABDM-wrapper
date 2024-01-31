/* (C) 2024 */
package com.nha.abdm.wrapper.hiu.hrp.consent.requests;

import com.nha.abdm.wrapper.common.models.Acknowledgement;
import com.nha.abdm.wrapper.common.models.RespRequest;
import com.nha.abdm.wrapper.common.responses.ErrorResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnNotifyRequest {
  private String requestId;
  private String timestamp;
  private List<Acknowledgement> acknowledgment;
  private ErrorResponse error;
  private RespRequest resp;
}
