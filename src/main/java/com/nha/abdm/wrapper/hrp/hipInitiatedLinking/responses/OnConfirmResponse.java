package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class OnConfirmResponse implements Serializable {
	private String requestId;
	private String timestamp;
	private AuthData auth;
	private String error;
	private RespData resp;

	public OnConfirmResponse() {
	}

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
		String requestId = getResp().getRequestId();
		return requestId + " " + getAuth().getAccessToken();
	}

	public static class AuthData {
		private String accessToken;

		public String getAccessToken() {
			return this.accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
	}

	public class RespData {
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
