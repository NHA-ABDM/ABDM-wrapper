package com.nha.abdm.wrapper.hrp.requestbody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.responseBody.NotifyResponse;
import com.nha.abdm.wrapper.hrp.utility.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.UUID;


@Component
public class OnNotifyBody {
    private static final Logger log = LogManager.getLogger(OnNotifyBody.class);
    @Autowired
    Utils utils;

    public HttpEntity<ObjectNode> makeBody(NotifyResponse data) throws URISyntaxException, JsonProcessingException {
//        log.info(data.printData());
        JsonNodeFactory nodeFactory=  JsonNodeFactory.instance;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("requestId", UUID.randomUUID().toString());
        requestBody.put("timestamp", utils.getCurrentTimeStamp().toString());
        //TODO
//        List<NotifyResponse.Notification
        ArrayNode ackArray=nodeFactory.arrayNode();
        for (NotifyResponse.Notification.ConsentArtefacts consentArtefact : data.getNotification().getConsentArtefacts()) {
            ObjectNode ackArrayNode = objectMapper.createObjectNode();
            ackArrayNode.put("status", "ok");
            ackArrayNode.put("consentId", consentArtefact.getId());
            ackArray.add(ackArrayNode);
        }
        requestBody.put("acknowledgement",ackArray);
        ObjectNode respNode = objectMapper.createObjectNode();
        respNode.put("requestId",data.getRequestId());
        requestBody.put("resp",respNode);
        HttpEntity<ObjectNode> requestEntity=new HttpEntity(requestBody, utils.initialiseHeadersForGateway());
        return requestEntity;
    }
}
