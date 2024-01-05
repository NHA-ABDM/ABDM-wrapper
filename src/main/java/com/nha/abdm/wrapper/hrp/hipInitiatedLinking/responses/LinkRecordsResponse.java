package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;


import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.helpers.PatientWithCareContexts;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.Serializable;
@Data
@Component
public class LinkRecordsResponse implements Serializable {
	private static final long serialVersionUID = 165269402517398406L;

	public String requestId;
	private String requesterId;
	private String patientReference;
	private String authMode;
	private PatientWithCareContexts patient;

	public LinkRecordsResponse() {
	}

}
