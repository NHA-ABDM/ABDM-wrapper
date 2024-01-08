package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nha.abdm.wrapper.hrp.CommonHelpers.ResponseHelper;
import com.nha.abdm.wrapper.hrp.common.CustomError;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.helpers.Acknowledgement;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
public class OnAddCareContextResponse implements Serializable {
	private static final long serialVersionUID = 165269402517398406L;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private Date timestamp;
	private Acknowledgement acknowledgement;
	private CustomError error;
	private ResponseHelper resp;
}
