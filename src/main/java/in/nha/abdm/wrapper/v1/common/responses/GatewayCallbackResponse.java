/* (C) 2024 */
package in.nha.abdm.wrapper.v1.common.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayCallbackResponse {
  private ErrorResponse error;
}
