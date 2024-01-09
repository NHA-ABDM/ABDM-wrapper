package com.nha.abdm.wrapper.hrp.discoveryLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.CommonHelpers.CareContextBuilder;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.common.Utils;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.ConfirmResponse;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class OnConfirmRequest {
    private SessionManager sessionManager;
    private ConfirmResponse data;
    private String abhaAddress;
    private String referenceNumber;
    private String display;
    private String linkRefNumber;
    private List<CareContextBuilder> careContexts;


    public static final Logger log = LogManager.getLogger(OnConfirmRequest.class);
    public HttpEntity<ObjectNode> makeRequest() throws URISyntaxException, JsonProcessingException {
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode requestBody = nodeFactory.objectNode();
        String requestId = UUID.randomUUID().toString();
        requestBody.put("requestId", requestId);
        requestBody.put("timestamp", Utils.getCurrentTimeStamp());
        String tokenNumber=data.getConfirmation().getToken();
        log.info("Token Number :"+tokenNumber);
        if(tokenNumber.equals("123456")){
            ObjectNode patientNode = nodeFactory.objectNode();
            log.info("linkRefNumber :"+linkRefNumber);
            log.info("onConfirm: Abha= "+abhaAddress);
            patientNode.put("referenceNumber", referenceNumber);
            patientNode.put("display", display);
            ArrayNode careContextArray = nodeFactory.arrayNode();
            Iterator list = careContexts.iterator();
            if(!careContexts.isEmpty()){
                while(list.hasNext()) {
                    CareContextBuilder careContext = (CareContextBuilder) list.next();
                    ObjectNode careContextNode = nodeFactory.objectNode();
                    careContextNode.put("referenceNumber", careContext.getReferenceNumber());
                    careContextNode.put("display", careContext.getDisplay());
                    careContextArray.add(careContextNode);
                }
                patientNode.set("careContexts", careContextArray);
                requestBody.put("patient",patientNode);
                requestBody.putNull("error");
            }else{
                requestBody.put("error","Retry again");
            }
            }else if(tokenNumber!="123456"){
            requestBody.put("error","Incorrect OTP");
        }
        ObjectNode respNode = nodeFactory.objectNode();
        respNode.put("requestId",data.getRequestId());
        requestBody.put("resp",respNode);
        log.info(requestBody.toPrettyString());
        HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, sessionManager.initialiseHeadersForGateway());
        return requestEntity;
    }

}
