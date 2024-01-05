
package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nha.abdm.wrapper.hrp.CommonHelpers.ResponseHelper;
import com.nha.abdm.wrapper.hrp.common.CustomError;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers.AuthData;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Data
@Component
public class OnInitResponse implements Serializable {
	private String requestId;
	private String timestamp;
	private AuthData auth;
	private ResponseHelper resp;
	private CustomError error;
}
