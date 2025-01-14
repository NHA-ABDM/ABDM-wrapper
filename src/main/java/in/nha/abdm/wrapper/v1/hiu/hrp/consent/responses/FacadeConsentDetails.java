/* (C) 2024 */
package in.nha.abdm.wrapper.v1.hiu.hrp.consent.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.nha.abdm.wrapper.v1.hiu.hrp.consent.requests.DateRange;
import in.nha.abdm.wrapper.v1.hiu.hrp.consent.requests.callback.ConsentStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacadeConsentDetails {
  private String deniedOn;
  private String grantedOn;
  private DateRange dateRange;
  private String dataEraseAt;
  private List<String> hiTypes;
  private List<ConsentStatus> consent;
}
