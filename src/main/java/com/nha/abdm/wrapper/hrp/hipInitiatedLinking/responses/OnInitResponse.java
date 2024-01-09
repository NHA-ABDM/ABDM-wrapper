
package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.nha.abdm.wrapper.hrp.CommonHelpers.ResponseHelper;
import com.nha.abdm.wrapper.hrp.CommonHelpers.CustomError;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers.AuthData;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Data
@Component
public class OnInitResponse implements Serializable {
	private static final long serialVersionUID = 165269402517398406L;
	private String requestId;
	private String timestamp;
	private AuthData auth;
	private ResponseHelper resp;
	private CustomError error;
}
