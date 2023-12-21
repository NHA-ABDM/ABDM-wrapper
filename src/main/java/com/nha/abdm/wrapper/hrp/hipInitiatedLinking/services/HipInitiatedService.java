package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.mongo.tables.Patient;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface HipInitiatedService {
    Object authInit(LinkRecordsResponse data) throws URISyntaxException, JsonProcessingException;

    ResponseEntity<ObjectNode> confirmAuth(OnInitResponse data) throws URISyntaxException, FileNotFoundException, JsonProcessingException;
//
    ResponseEntity<ObjectNode> addContext(OnConfirmResponse data) throws Exception;
    void addContextAccessToken(LinkRecordsResponse data, String accessToken, Patient patient) throws URISyntaxException, FileNotFoundException, JsonProcessingException;
}
