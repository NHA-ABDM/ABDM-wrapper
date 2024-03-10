/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.dataTransfer.encryption;

import com.nha.abdm.wrapper.hip.hrp.dataTransfer.requests.helpers.HealthInformationBundle;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncryptionResponse {
  private List<HealthInformationBundle> healthInformationBundles;
  private String keyToShare;
  private String senderNonce;
}
