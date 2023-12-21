
package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class OnInitResponse implements Serializable {
	private String requestId;
	private String timestamp;
	private AuthData auth;
	private String error;
	private RespData resp;

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public AuthData getAuth() {
		return this.auth;
	}

	public void setAuth(AuthData auth) {
		this.auth = auth;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public RespData getResp() {
		return this.resp;
	}

	public void setResp(RespData resp) {
		this.resp = resp;
	}

	public String printData() {

		return "1.requestId: " + this.requestId + "TransactionId: " + this.getAuth().getTransactionId() + "getResp Response: " + this.getResp().getRequestId();
	}

	public static class AuthData {
		@JsonProperty("transactionId")
		private String transactionId;
		private String mode;

		public AuthData() {
		}

		public String getTransactionId() {
			return this.transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}

		public String getMode() {
			return this.mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}
	}

	public static class RespData {
		@JsonProperty("requestId")
		private String requestId;

		public RespData() {
		}

		public String getRequestId() {
			return this.requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}
	}
}
