/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses;

import com.nha.abdm.wrapper.common.models.RespRequest;
import com.nha.abdm.wrapper.common.responses.ErrorResponse;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.helpers.LinkAcknowledgement;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LinkOnAddCareContextsResponse implements Serializable {
  private static final long serialVersionUID = 165269402517398406L;
  private String timestamp;
  private LinkAcknowledgement acknowledgement;
  private ErrorResponse error;
  private RespRequest resp;
}
