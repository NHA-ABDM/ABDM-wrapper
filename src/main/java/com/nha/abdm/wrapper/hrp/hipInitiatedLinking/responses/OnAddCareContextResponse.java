package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nha.abdm.wrapper.hrp.CommonHelpers.ResponseHelper;
import com.nha.abdm.wrapper.hrp.common.CustomError;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers.Acknowledgement;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class OnAddCareContextResponse {
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private Date timestamp;
	private Acknowledgement acknowledgement;
	private CustomError error;
	public String printData;
	private ResponseHelper resp;

}
