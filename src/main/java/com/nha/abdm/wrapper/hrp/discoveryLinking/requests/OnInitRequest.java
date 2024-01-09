package com.nha.abdm.wrapper.hrp.discoveryLinking.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.CommonHelpers.CustomError;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.common.Utils;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.InitResponse;
import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import java.net.URISyntaxException;
import java.util.UUID;

@Builder
public class OnInitRequest {
    private SessionManager sessionManager;
    private CustomError customError;
    private static final Logger log = LogManager.getLogger(OnInitRequest.class);
    private InitResponse data;
    public HttpEntity<ObjectNode> makeRequest() throws URISyntaxException, JsonProcessingException {
        log.info(data.toString());
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode requestBody = nodeFactory.objectNode();
        String requestId = UUID.randomUUID().toString();
        requestBody.put("requestId", requestId);
        requestBody.put("timestamp", Utils.getCurrentTimeStamp());
        requestBody.put("transactionId", data.getTransactionId());
        if(customError.getMessage()!=null){
            ObjectNode errorNode = nodeFactory.objectNode();
            errorNode.put("code",customError.getCode());
            errorNode.put("message",customError.getMessage());
            requestBody.put("error",errorNode);
        }else {
            ObjectNode linkNode = nodeFactory.objectNode();
            linkNode.put("referenceNumber", UUID.randomUUID().toString());
            linkNode.put("authenticationType", "DIRECT");
            ObjectNode metaNode = nodeFactory.objectNode();
            metaNode.put("communicationMedium", "MOBILE");
            metaNode.put("communicationHint", "string");
            metaNode.put("communicationExpiry", Utils.getSmsExpiry());
            linkNode.put("meta", metaNode);
            requestBody.put("link", linkNode);
            requestBody.putNull("error");
        }
        ObjectNode respNode = nodeFactory.objectNode();
        respNode.put("requestId",data.getRequestId());
        requestBody.put("resp",respNode);
        HttpEntity<ObjectNode> requestEntity = new HttpEntity(requestBody, sessionManager.initialiseHeadersForGateway());
        log.info(requestEntity.getHeaders().toString());
        log.info(((ObjectNode)requestEntity.getBody()).toPrettyString());
        return requestEntity;

    }
}
