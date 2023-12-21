package com.nha.abdm.wrapper.hrp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.responseBody.InitiateConsentResponse;
import com.nha.abdm.wrapper.hrp.responseBody.NotifyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.List;


@Component
public interface ConsentManager {


    ResponseEntity<ObjectNode> initiateConsent(InitiateConsentResponse data) throws URISyntaxException, JsonProcessingException;

    ResponseEntity<ObjectNode> onNotify(NotifyResponse data) throws URISyntaxException, JsonProcessingException;

    ResponseEntity<ObjectNode> fetchCall(NotifyResponse data, List<NotifyResponse.Notification.ConsentArtefacts> consentArtefactsList,int item) throws URISyntaxException, JsonProcessingException;

}

