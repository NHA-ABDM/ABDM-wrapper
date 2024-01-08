package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.nha.abdm.wrapper.hrp.serviceImpl.CareContextTableService;
import com.nha.abdm.wrapper.hrp.common.CareContextBuilder;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.common.Utils;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Builder
public class CareContextRequest {

	private SessionManager sessionManager;
	private OnConfirmResponse data;
	private Patients patients;
	private List<CareContextBuilder> careContexts;
	private static final Logger log = LogManager.getLogger(CareContextRequest.class);

	public HttpEntity<ObjectNode> makeRequest() throws FileNotFoundException, URISyntaxException, JsonProcessingException {
		log.info("In CareContextRequest");
		JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode requestBody = nodeFactory.objectNode();
		String careContextRequestID = UUID.randomUUID().toString();
		requestBody.put("requestId", careContextRequestID);
		requestBody.put("timestamp", Utils.getCurrentTimeStamp());
		ObjectNode linkNode = nodeFactory.objectNode();
		linkNode.put("accessToken", data.getAuth().getAccessToken());
		ObjectNode patientNode = nodeFactory.objectNode();
		patientNode.put("referenceNumber", patients.getPatientReference());
		patientNode.put("display", patients.getDisplay());
		ArrayNode careContextArray = nodeFactory.arrayNode();
		Iterator list = careContexts.iterator();
		while(list.hasNext()) {
			CareContextBuilder careContext = (CareContextBuilder) list.next();
			ObjectNode careContextNode = nodeFactory.objectNode();
			careContextNode.put("referenceNumber", careContext.getReferenceNumber());
			careContextNode.put("display", careContext.getDisplay());
			careContextArray.add(careContextNode);
		}
		patientNode.set("careContexts", careContextArray);
		linkNode.set("patient", patientNode);
		requestBody.set("link", linkNode);
		HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, sessionManager.initialiseHeadersForGateway());
		return requestEntity;
		}}


