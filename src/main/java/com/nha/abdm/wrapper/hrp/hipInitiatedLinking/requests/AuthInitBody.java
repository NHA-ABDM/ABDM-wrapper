package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.nha.abdm.wrapper.hrp.serviceImpl.CareContextTableService;
import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;
import com.nha.abdm.wrapper.hrp.serviceImpl.PatientTableService;
import com.nha.abdm.wrapper.hrp.controller.GatewayService;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.common.Utils;

import java.net.URISyntaxException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthInitBody {
	private static final Logger log = LogManager.getLogger(AuthInitBody.class);
	@Autowired
	Utils utils;



	public HttpEntity<ObjectNode> makeRequest(LinkRecordsResponse data,String abhaAddress) throws URISyntaxException, JsonProcessingException {
		JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode requestBody = nodeFactory.objectNode();
		String requestId = data.getRequestId();
		requestBody.put("requestId", requestId);
		requestBody.put("timestamp", this.utils.getCurrentTimeStamp().toString());
		ObjectNode queryNode = nodeFactory.objectNode();
		queryNode.put("id", abhaAddress);
		queryNode.put("purpose", "KYC_AND_LINK");
		String authMode = data.getAuthMode();
		queryNode.put("authMode", authMode);
		ObjectNode requesterNode = nodeFactory.objectNode();
		requesterNode.put("type", "HIP");
		requesterNode.put("id", data.getRequesterId());
		queryNode.set("requester", requesterNode);
		requestBody.set("query", queryNode);
		HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, this.utils.initialiseHeadersForGateway());
		log.info(requestEntity.getHeaders().toString());
		log.info(((ObjectNode)requestEntity.getBody()).toPrettyString());
		return requestEntity;
	}
}
