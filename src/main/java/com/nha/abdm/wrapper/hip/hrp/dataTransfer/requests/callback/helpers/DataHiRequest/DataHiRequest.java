/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.dataTransfer.requests.callback.helpers.DataHiRequest;

import com.nha.abdm.wrapper.hip.hrp.dataTransfer.requests.callback.helpers.DataHiRequest.DatakeyMaterial.DataKeyMaterial;
import com.nha.abdm.wrapper.hip.hrp.dataTransfer.requests.callback.helpers.DataNotification.DataConsentdetail.DataConsentManager.DataConsentManager;
import com.nha.abdm.wrapper.hip.hrp.dataTransfer.requests.callback.helpers.DataNotification.DataConsentdetail.DataPermission.DataDateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DataHiRequest {
  public DataConsentManager consent;
  public String dataPushUrl;
  public DataDateRange dateRange;
  public DataKeyMaterial keyMaterial;
}