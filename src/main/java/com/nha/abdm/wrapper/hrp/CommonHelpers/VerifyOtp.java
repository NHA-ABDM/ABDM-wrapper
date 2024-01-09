package com.nha.abdm.wrapper.hrp.CommonHelpers;

import lombok.Data;

@Data
public class VerifyOtp {
    private String loginHint;
    private String requestId;
    private String authCode;
}
