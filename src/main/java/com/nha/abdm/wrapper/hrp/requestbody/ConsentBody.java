package com.nha.abdm.wrapper.hrp.requestbody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.nha.abdm.wrapper.hrp.responseBody.InitiateConsentResponse;
import com.nha.abdm.wrapper.hrp.utility.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Component
public class ConsentBody {
    private static final Logger log = LogManager.getLogger(ConsentBody.class);
    String requestId;
    String timestamp;
    String id;
    String purpose;
    String requesterType="HIU";
    String headerValue;
    String patient;
    @Autowired
    Utils utils;
    public HttpEntity<ObjectNode> makeBody(InitiateConsentResponse frontendData) throws URISyntaxException, JsonProcessingException {
        JsonNodeFactory nodeFactory=  JsonNodeFactory.instance;

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("requestId", UUID.randomUUID().toString());
        requestBody.put("timestamp", utils.getCurrentTimeStamp().toString());

        ObjectNode consentNode = objectMapper.createObjectNode();
        ObjectNode purposeNode = objectMapper.createObjectNode();
        purposeNode.put("text",frontendData.getConsent().getPurpose().getText());
        purposeNode.put("code",frontendData.getConsent().getPurpose().getCode());
        consentNode.set("purpose",purposeNode);
        ObjectNode patientNode= objectMapper.createObjectNode();
        patientNode.put("id",frontendData.getConsent().getPatient().getId());
        consentNode.set("patient",patientNode);
        ObjectNode hiuNode= objectMapper.createObjectNode();
        hiuNode.put("id",frontendData.getConsent().getHiu().getId());
        consentNode.set("hiu",hiuNode);
        ObjectNode requesterNode = objectMapper.createObjectNode();
        requesterNode.put("name", frontendData.getConsent().getRequester().getName());
        ObjectNode identifierNode = objectMapper.createObjectNode();
        identifierNode.put("type", frontendData.getConsent().getRequester().getIdentifier().getType());
        identifierNode.put("value", frontendData.getConsent().getRequester().getIdentifier().getValue());
        identifierNode.put("system", frontendData.getConsent().getRequester().getIdentifier().getSystem());
        requesterNode.set("identifier", identifierNode);
        consentNode.set("requester", requesterNode);
        List<String> hiTypes=frontendData.getConsent().getHiTypes();
        ArrayNode jsonArray = JsonNodeFactory.instance.arrayNode();
        for (String value : hiTypes) {
            jsonArray.add(value);
        }
        consentNode.put("hiTypes", jsonArray);//TODO
        ObjectNode permissionNode = objectMapper.createObjectNode();
        permissionNode.put("accessMode", frontendData.getConsent().getPermission().getAccessMode());
        ObjectNode dateRangeNode = objectMapper.createObjectNode();
        dateRangeNode.put("from", frontendData.getConsent().getPermission().getDateRange().getFrom());
        dateRangeNode.put("to", frontendData.getConsent().getPermission().getDateRange().getTo());
        permissionNode.set("dateRange", dateRangeNode);
        permissionNode.put("dataEraseAt", frontendData.getConsent().getPermission().getDataEraseAt());
        ObjectNode frequencyNode = objectMapper.createObjectNode();
        frequencyNode.put("unit", frontendData.getConsent().getPermission().getFrequency().getUnit());
        frequencyNode.put("value", frontendData.getConsent().getPermission().getFrequency().getValue());
        frequencyNode.put("repeats", frontendData.getConsent().getPermission().getFrequency().getRepeats());
        permissionNode.set("frequency", frequencyNode);
        consentNode.set("permission", permissionNode);
        requestBody.set("consent", consentNode);
        log.info(requestBody.toPrettyString());
        return new HttpEntity<ObjectNode>(requestBody, utils.initialiseHeadersForGateway());
    }

    private Object getField(String fieldName, JsonNode frontendData) {
        return frontendData.has(fieldName) ? frontendData.get(fieldName) : null;
    }
}
