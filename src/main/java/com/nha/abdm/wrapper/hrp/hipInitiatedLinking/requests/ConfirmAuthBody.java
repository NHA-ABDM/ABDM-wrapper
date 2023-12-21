package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.nha.abdm.wrapper.hrp.serviceImpl.CareContextTableService;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.common.Utils;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class ConfirmAuthBody {
	private static final Logger log = LogManager.getLogger(ConfirmAuthBody.class);
	@Autowired
	Utils utils;


	public HttpEntity<ObjectNode> makeRequest(OnInitResponse data,String patientName,String patientGender, String patientDob) throws FileNotFoundException, URISyntaxException, JsonProcessingException {
		JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode requestBody = nodeFactory.objectNode();
		String confirmRequestId = UUID.randomUUID().toString();
		requestBody.put("requestId", confirmRequestId);
		requestBody.put("timestamp", this.utils.getCurrentTimeStamp().toString());
		String initRequestId = data.getResp().getRequestId();
		log.info("initRequestID: " + initRequestId + " confirmRequestId: " + confirmRequestId);
		requestBody.put("transactionId", data.getAuth().getTransactionId());
		log.info("Started Confirm auth after storing transactionId");
		ObjectNode credentialNode = nodeFactory.objectNode();
		if (data.getAuth().getMode().equals("DEMOGRAPHICS")) {
			ObjectNode demographicNode = nodeFactory.objectNode();
			demographicNode.put("name", patientName);
			demographicNode.put("gender", patientGender);
			demographicNode.put("dateOfBirth", patientDob);
			credentialNode.set("demographic", demographicNode);
		}
		requestBody.set("credential", credentialNode);
		log.info("ConfirmBody : " + requestBody.toPrettyString());
		HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, this.utils.initialiseHeadersForGateway());
		return requestEntity;
	}
}
