package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.nha.abdm.wrapper.hrp.serviceImpl.CareContextTableService;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.common.Utils;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;


@Builder
public class ConfirmAuthBody {
	private static final Logger log = LogManager.getLogger(ConfirmAuthBody.class);

	private SessionManager sessionManager;
	private OnInitResponse data;
	private Patients patients;
	private String otp;


	public HttpEntity<ObjectNode> makeRequest() throws FileNotFoundException, URISyntaxException, JsonProcessingException, TimeoutException {
		JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode requestBody = nodeFactory.objectNode();
		String confirmRequestId = UUID.randomUUID().toString();
		requestBody.put("requestId", confirmRequestId);
		requestBody.put("timestamp", Utils.getCurrentTimeStamp());
		String initRequestId = data.getResp().getRequestId();
		requestBody.put("transactionId", data.getAuth().getTransactionId());
		log.info("Started Confirm auth after storing transactionId");
		ObjectNode credentialNode = nodeFactory.objectNode();
		if (data.getAuth().getMode().equals("DEMOGRAPHICS")) {
			ObjectNode demographicNode = nodeFactory.objectNode();
			demographicNode.put("name", patients.getName());
			demographicNode.put("gender", patients.getGender());
			demographicNode.put("dateOfBirth", patients.getDateOfBirth());
			credentialNode.set("demographic", demographicNode);
		}else if(data.getAuth().getMode().equals("MOBILE_OTP")){
			credentialNode.put("authCode", otp);
		}
		requestBody.set("credential", credentialNode);
		log.info("ConfirmBody : " + requestBody.toPrettyString());
		HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, sessionManager.initialiseHeadersForGateway());
		return requestEntity;
	}
}
