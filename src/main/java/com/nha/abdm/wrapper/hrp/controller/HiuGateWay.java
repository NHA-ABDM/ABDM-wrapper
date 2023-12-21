package com.nha.abdm.wrapper.hrp.controller;

import com.nha.abdm.wrapper.hrp.responseBody.InitiateConsentResponse;
import com.nha.abdm.wrapper.hrp.responseBody.NotifyResponse;
import com.nha.abdm.wrapper.hrp.responseBody.OnFetchBody;
import com.nha.abdm.wrapper.hrp.services.CommonServices;
import com.nha.abdm.wrapper.hrp.services.ConsentManager;
import com.nha.abdm.wrapper.hrp.utility.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class HiuGateWay {
    @Autowired
    ConsentManager consentManager;

    @Autowired
    Utils utils;

    @Autowired
    CommonServices commonServices;
    private static final Logger log = LogManager.getLogger(HiuGateWay.class);

    public String initiateConsent(InitiateConsentResponse data) throws URISyntaxException, JsonProcessingException {
        if(utils.getAccessToken()==null) commonServices.startSession();
        ResponseEntity<ObjectNode> startConsent= consentManager.initiateConsent(data);
        return startConsent.getStatusCode().toString();
    }
   // public String startOnInit(OnInitResponse data) throws URISyntaxException, JsonProcessingException {
    // if(commonUtility.getAccessToken()==null) commonServices.startSession();
   //return startConsent.getStatusCode().toString();
  //  }


    public void startOnNotify(NotifyResponse data) throws URISyntaxException, JsonProcessingException {
        ResponseEntity<ObjectNode> startConsent= consentManager.onNotify(data);

    }

    public void startFetch(NotifyResponse data, List<NotifyResponse.Notification.ConsentArtefacts> consentArtefactsList, int item) throws URISyntaxException, JsonProcessingException {
        ResponseEntity<ObjectNode> startConsent= consentManager.fetchCall(data,consentArtefactsList,item);

    }

    public void startRequest(OnFetchBody data) {
    }
//    public String startOnNotifyCall(OnNotifyBody data){
//
//
//    }



}
