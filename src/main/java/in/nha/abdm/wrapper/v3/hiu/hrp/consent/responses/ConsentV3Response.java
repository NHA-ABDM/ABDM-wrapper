/* (C) 2024 */
package in.nha.abdm.wrapper.v3.hiu.hrp.consent.responses;

import in.nha.abdm.wrapper.v1.common.models.Consent;
import in.nha.abdm.wrapper.v1.hip.hrp.database.mongo.tables.helpers.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentV3Response {
  private RequestStatus status;
  private Object errors;
  private HttpStatusCode httpStatusCode;
  private Consent consent;
}
