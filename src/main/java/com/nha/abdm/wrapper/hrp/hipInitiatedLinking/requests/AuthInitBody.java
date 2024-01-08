package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.common.Utils;

import java.net.URISyntaxException;
import java.util.UUID;

import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

@Builder
public class AuthInitBody {
	private static final Logger log = LogManager.getLogger(AuthInitBody.class);
	private SessionManager sessionManager;
	private LinkRecordsResponse data;
	private String abhaAddress;


	public HttpEntity<ObjectNode> makeRequest() throws URISyntaxException, JsonProcessingException {
		JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode requestBody = nodeFactory.objectNode();
		String requestId = data.getRequestId();
		requestBody.put("requestId", requestId);
		requestBody.put("timestamp", Utils.getCurrentTimeStamp());
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
		HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, sessionManager.initialiseHeadersForGateway());
		log.info(requestEntity.getHeaders().toString());
		log.info(((ObjectNode)requestEntity.getBody()).toPrettyString());
		return requestEntity;
	}
}
