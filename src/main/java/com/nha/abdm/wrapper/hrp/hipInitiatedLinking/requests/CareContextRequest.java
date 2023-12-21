package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.nha.abdm.wrapper.hrp.serviceImpl.CareContextTableService;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.common.Utils;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class CareContextRequest {
	@Autowired
	Utils utils;
	private static final Logger log = LogManager.getLogger(CareContextRequest.class);

	public HttpEntity<ObjectNode> makeRequest(OnConfirmResponse data,String patientReference,String patientDisplay,List<LinkRecordsResponse.CareContext> careContexts,String accessToken) throws FileNotFoundException, URISyntaxException, JsonProcessingException {
		log.info("In CareContextRequest");
		JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode requestBody = nodeFactory.objectNode();
		String careContextRequestID = UUID.randomUUID().toString();
		requestBody.put("requestId", careContextRequestID);
		requestBody.put("timestamp", utils.getCurrentTimeStamp());
		ObjectNode linkNode = nodeFactory.objectNode();
		log.info("in OnConfirmResponse function");
		log.info("Add CareContextBody: " + data.printData());
		linkNode.put("accessToken", data.getAuth().getAccessToken());
		ObjectNode patientNode = nodeFactory.objectNode();
		patientNode.put("referenceNumber", patientReference);
		patientNode.put("display", patientDisplay);
		ArrayNode careContextArray = nodeFactory.arrayNode();
		Iterator list = careContexts.iterator();
		while(list.hasNext()) {
			LinkRecordsResponse.CareContext careContext = (LinkRecordsResponse.CareContext)list.next();
			ObjectNode careContextNode = nodeFactory.objectNode();
			careContextNode.put("referenceNumber", careContext.getReferenceNumber());
			careContextNode.put("display", careContext.getDisplay());
			careContextArray.add(careContextNode);
		}
		patientNode.set("careContexts", careContextArray);
		linkNode.set("patient", patientNode);
		requestBody.set("link", linkNode);
		HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, this.utils.initialiseHeadersForGateway());
		return requestEntity;
		}}


