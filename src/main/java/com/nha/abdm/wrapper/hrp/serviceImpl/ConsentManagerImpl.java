package com.nha.abdm.wrapper.hrp.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nha.abdm.wrapper.hrp.properties.ApplicationConfig;
import com.nha.abdm.wrapper.hrp.requestbody.ConsentBody;
import com.nha.abdm.wrapper.hrp.requestbody.FetchBody;
import com.nha.abdm.wrapper.hrp.requestbody.OnNotifyBody;
import com.nha.abdm.wrapper.hrp.responseBody.InitiateConsentResponse;
import com.nha.abdm.wrapper.hrp.responseBody.NotifyResponse;
import com.nha.abdm.wrapper.hrp.services.CommonServices;
import com.nha.abdm.wrapper.hrp.services.ConsentManager;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.utility.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ConsentManagerImpl implements ConsentManager {
    private static final Logger log = LogManager.getLogger(ConsentManagerImpl.class);
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    ConsentBody consentBody;

    @Autowired
    OnNotifyBody onNotifyBody;
    RestTemplate restTemplate= new RestTemplate();
    @Autowired
    LogsTableService logsTableService;

    @Autowired
    Utils utils;

    @Autowired
    CommonServices commonServices;
    @Override
    public ResponseEntity<ObjectNode> initiateConsent(InitiateConsentResponse data) throws URISyntaxException, JsonProcessingException {
        if(utils.getAccessToken()==null) commonServices.startSession();
        HttpEntity<ObjectNode> requestEntity= consentBody.makeBody(data);
        ResponseEntity<ObjectNode> responseEntity = restTemplate.exchange(
                new URI(applicationConfig.initApi),
                HttpMethod.POST,
                requestEntity,
                ObjectNode.class
        );
        log.info(responseEntity.getStatusCode());
        logsTableService.setRequestId(data.getRequestId(),requestEntity.getBody().get("requestId").asText(),responseEntity.getStatusCode().toString());
      return responseEntity;
    }


    @Override
    public ResponseEntity<ObjectNode> onNotify(NotifyResponse data) throws URISyntaxException, JsonProcessingException {
        if(utils.getAccessToken()==null) commonServices.startSession();
        HttpEntity<ObjectNode> requestEntity= onNotifyBody.makeBody(data);
        ResponseEntity<ObjectNode> responseEntity = restTemplate.exchange(
                new URI(applicationConfig.onNotify),
                HttpMethod.POST,
                requestEntity,
                ObjectNode.class
        );
        log.info(responseEntity.getStatusCode());
        return null;
    }

    @Override
    public ResponseEntity<ObjectNode> fetchCall(NotifyResponse data, List<NotifyResponse.Notification.ConsentArtefacts> consentArtefactsList, int item) throws URISyntaxException, JsonProcessingException {
        if(utils.getAccessToken()==null) commonServices.startSession();
        ResponseEntity<ObjectNode> responseEntity = restTemplate.exchange(
                new URI(applicationConfig.fetch),
                HttpMethod.POST,
                FetchBody.makeBody(data,consentArtefactsList,item),
                ObjectNode.class
        );
        log.info(responseEntity.getStatusCode());
        return null;

    }

}
