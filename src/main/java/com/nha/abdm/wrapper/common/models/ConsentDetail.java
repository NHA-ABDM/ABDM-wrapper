/* (C) 2024 */
package com.nha.abdm.wrapper.common.models;

import com.nha.abdm.wrapper.hiu.hrp.consent.requests.*;
import com.nha.abdm.wrapper.hiu.hrp.consent.requests.callback.OnFetchConsentManager;
import com.nha.abdm.wrapper.hiu.hrp.consent.requests.callback.OnFetchConsentPatient;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsentDetail {
  private String schemaVersion;
  private String consentId;
  private String createdAt;
  private OnFetchConsentPatient patient;
  private List<ConsentCareContexts> careContexts;
  private Purpose purpose;
  private ConsentHIP hip;
  private ConsentHIU hiu;
  private OnFetchConsentManager consentManager;
  private ConsentRequester requester;
  private List<String> hiTypes;
  private Permission permission;
  private String signature;
}
