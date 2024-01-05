package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthData {
    private String accessToken;
    private String transactionId;
    private String mode;
}
