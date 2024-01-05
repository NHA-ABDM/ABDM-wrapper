package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.common.CustomError;
import com.nha.abdm.wrapper.hrp.common.GatewayApiPaths;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests.CareContextRequest;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests.ConfirmAuthBody;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.services.HipInitiatedService;
import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import com.nha.abdm.wrapper.hrp.mongo.tables.RequestLogs;
import com.nha.abdm.wrapper.hrp.properties.ApplicationConfig;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests.AuthInitBody;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.repository.LogsRepo;
import com.nha.abdm.wrapper.hrp.repository.PatientRepo;
import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;
import com.nha.abdm.wrapper.hrp.serviceImpl.PatientTableService;
import com.nha.abdm.wrapper.hrp.serviceImpl.TokenManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class HipInitiatedServiceImpl implements HipInitiatedService {
    private static final Logger log = LogManager.getLogger(HipInitiatedServiceImpl.class);
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    AuthInitBody authInitBody;
    @Autowired
    ConfirmAuthBody confirmAuthBody;

    @Autowired
    CareContextRequest careContextRequest;
    @Autowired
    LogsTableService logsTableService;

    @Autowired
    CareContextRequest careContextRequestOnConfirm;
    @Autowired
    CareContextRequest careContextRequestLinkedResponse;
    @Autowired
    GatewayApiPaths gatewayApiPaths;
    @Autowired
    PatientTableService patientTableService;

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    LogsRepo logsRepo;
    @Autowired
    CustomError customError;
    @Autowired
    TokenManagementService tokenManagementService;

    public Object authInit(LinkRecordsResponse data) throws URISyntaxException, JsonProcessingException {
        try{
            Patients existingRecord=patientRepo.findByPatientReference(data.getPatientReference());
            if(existingRecord.getAbhaAddress()==null){
                customError.setCode(10001);
                customError.setMessage("Abha address not found with patient UID , kindly add patient");
                log.info("Patient not found");
                logsTableService.setContent(data,new HttpEntity<>(new ObjectNode(JsonNodeFactory.instance).put("Error","Patient not found, kindly add patient")), Object.class);
                return customError;
            }
            patientTableService.addPatient(data);
            HttpEntity<ObjectNode> requestEntity = authInitBody.makeRequest(data,existingRecord.getAbhaAddress());
            ResponseEntity<ObjectNode> responseEntity = this.restTemplate.exchange(new URI(gatewayApiPaths.LINK_AUTH_INIT), HttpMethod.POST, requestEntity, ObjectNode.class);
            log.info("LinkRecords storing data");
            logsTableService.setContent(data,requestEntity, LinkRecordsResponse.class);
            log.info(responseEntity.getStatusCode());
            ObjectNode responseData = new ObjectNode(JsonNodeFactory.instance);
            responseData.put("Status Code : ", responseEntity.getStatusCode().toString());
            responseData.put("requestId :", ((ObjectNode)requestEntity.getBody()).get("requestId"));
            return responseData;

        }catch(Exception e){
            log.info("authInit : "+e);
            customError.setCode(1000);
            customError.setMessage("Found many patients with same patientReference");
            return customError;
        }
    }
    public ResponseEntity<ObjectNode> confirmAuth(@RequestBody OnInitResponse data) throws URISyntaxException, JsonProcessingException, FileNotFoundException {
        RequestLogs existingRecord=logsRepo.findByRequestId(data.getResp().getRequestId());
        if(existingRecord!=null){
            log.info("In confirmAuth found existing record");
            LinkRecordsResponse linkRecordsResponse=(LinkRecordsResponse) existingRecord.getRawResponse().get("LinkRecordsResponse");
            Patients patient=patientRepo.findByPatientReference(linkRecordsResponse.getPatientReference());
            HttpEntity<ObjectNode> requestEntity=confirmAuthBody.makeRequest(data,patient.getName(),patient.getGender(),patient.getDateOfBirth());
            ResponseEntity<ObjectNode> responseEntity = this.restTemplate.exchange(new URI(gatewayApiPaths.LINK_CONFIRM_AUTH), HttpMethod.POST, requestEntity, ObjectNode.class);
            log.info("getConfirmAuth :" + responseEntity.getStatusCode());
            logsTableService.setContent(data,requestEntity, OnInitResponse.class);
            return responseEntity;
        }log.info("In confirmAuth not found existing record");
        return null;

    }
    public ResponseEntity<ObjectNode> addContext(OnConfirmResponse data) throws Exception {
        RequestLogs existingRecord=logsRepo.findByGatewayRequestId(data.getResp().getRequestId());
        if(existingRecord!=null){
            log.info("in addContext found existing record");
            LinkRecordsResponse linkRecordsResponse=(LinkRecordsResponse)  existingRecord.getRawResponse().get("LinkRecordsResponse");
            Patients patient=patientRepo.findByPatientReference(linkRecordsResponse.getPatientReference());
            HttpEntity<ObjectNode> requestEntity=careContextRequest.makeRequest(data,patient.getPatientReference(),patient.getDisplay(),linkRecordsResponse.getPatient().getCareContexts(),null);
            ResponseEntity<ObjectNode> responseEntity = this.restTemplate.exchange(new URI(gatewayApiPaths.LINK_ADD_CARE_CONTEXT), HttpMethod.POST, requestEntity, ObjectNode.class);
            logsTableService.setContent(data,requestEntity, OnConfirmResponse.class);
            tokenManagementService.setToken(patient.getAbhaAddress(),data);
            return responseEntity;
        }
        return null;

    }
    public void addContextAccessToken(LinkRecordsResponse data,String accessToken,Patients patient) throws URISyntaxException, FileNotFoundException, JsonProcessingException {
//        HttpEntity<ObjectNode> requestEntity=careContextRequest.makeRequest(data,patient.getPatientReference(),patient.getDisplay(),data.getPatient().getCareContexts(),accessToken);
//        ResponseEntity<ObjectNode> responseEntity = this.restTemplate.exchange(new URI(gatewayApi.link_addCareContext), HttpMethod.POST, requestEntity, ObjectNode.class);
//        if(responseEntity.getStatusCode().is2xxSuccessful()) log.info("without accessToken : add careContext success");
//        else log.info("without accessToken : add careContext failed");
    }
}
