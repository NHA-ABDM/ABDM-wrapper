package com.nha.abdm.wrapper.hrp.requestbody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.responseBody.NotifyResponse;
import com.nha.abdm.wrapper.hrp.utility.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Component
public class FetchBody {
    private static final Logger log = LogManager.getLogger(ConsentBody.class);
    @Autowired
    static Utils utils;
    public static HttpEntity<ObjectNode> makeBody(NotifyResponse data, List<NotifyResponse.Notification.ConsentArtefacts> consentArtefactsList, int item) throws URISyntaxException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("requestId", UUID.randomUUID().toString());
        requestBody.put("timestamp", utils.getCurrentTimeStamp().toString());
        requestBody.put("consentId",consentArtefactsList.get(item).getId());
        log.info(consentArtefactsList.get(item).getId());
        HttpEntity<ObjectNode> requestEntity=new HttpEntity(requestBody, utils.initialiseHeadersForGateway());
        return requestEntity;
    }
}
