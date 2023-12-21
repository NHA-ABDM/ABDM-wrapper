package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class OnAddCareContextResponse {
	@JsonFormat(
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
	)
	private Date timestamp;
	private Acknowledgement acknowledgement;
	private String error;
	public String printData;
	private Response resp;

	public OnAddCareContextResponse() {
		Date var10001 = this.getTimestamp();
		this.printData = "timestamp :" + var10001 + ",acknowledgement.status : " + this.getAcknowledgement() + " error :" + this.getError() + ",resp.requestId :getResp().requestId";
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Acknowledgement getAcknowledgement() {
		return this.acknowledgement;
	}

	public void setAcknowledgement(Acknowledgement acknowledgement) {
		this.acknowledgement = acknowledgement;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Response getResp() {
		return this.resp;
	}

	public void setResp(Response resp) {
		this.resp = resp;
	}

	public static class Acknowledgement {
		private String status;

		public Acknowledgement() {
		}

		public String getStatus() {
			return this.status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

	public static class Response {
		private String requestId;

		public Response() {
		}

		public String getRequestId() {
			return this.requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
	}
}
