package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nha.abdm.wrapper.hrp.CommonHelpers.ResponseHelper;
import com.nha.abdm.wrapper.hrp.common.CustomError;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers.AuthData;
import lombok.Data;

import java.io.Serializable;
@Data
public class OnConfirmResponse implements Serializable {
	private static final long serialVersionUID = 165269402517398406L;
	private String requestId;
	private String timestamp;
	private AuthData auth;
	private CustomError error;
	private ResponseHelper resp;
}
