package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

//import com.nha.abdm.wrapper.hrp.CareContextService;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class LinkRecordsResponse implements Serializable {
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String requestId;
	private String requesterId;

	private String patientReference;

	public String getPatientReference() {
		return patientReference;
	}

	public void setPatientReference(String patientReference) {
		this.patientReference = patientReference;
	}

	private String authMode;
//	private String name;
//	private String gender;
//	private String dateOfBirth;
	private PatientData patient;

	public LinkRecordsResponse() {
	}

	public String getRequesterId() {
		return this.requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}


	public String getAuthMode() {
		return this.authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}


	public PatientData getPatient() {
		return this.patient;
	}

	public void setPatient(PatientData patient) {
		this.patient = patient;
	}

	public static class PatientData {
		private List<CareContext> careContexts;

		public List<CareContext> getCareContexts() {
			return this.careContexts;
		}

		public void setCareContexts(List<CareContext> careContexts) {
			this.careContexts = careContexts;
		}
	}

	public static class CareContext  {
		public String referenceNumber;
		private String display;

		public String getReferenceNumber() {
			return this.referenceNumber;
		}

		public void setReferenceNumber(String referenceNumber) {
			this.referenceNumber = referenceNumber;
		}

		public String getDisplay() {
			return this.display;
		}

		public void setDisplay(String display) {
			this.display = display;
		}
	}
}
