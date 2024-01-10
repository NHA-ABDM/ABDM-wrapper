package com.nha.abdm.wrapper.hrp.hipInitiatedLinking.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.CommonHelpers.CustomError;
import com.nha.abdm.wrapper.hrp.common.GatewayApiPaths;
import com.nha.abdm.wrapper.hrp.common.MakeRequest;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests.CareContextRequest;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests.ConfirmAuthBody;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.services.HipInitiatedService;
import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import com.nha.abdm.wrapper.hrp.mongo.tables.RequestLogs;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.requests.AuthInitBody;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.repository.LogsRepo;
import com.nha.abdm.wrapper.hrp.repository.PatientRepo;
import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;
import com.nha.abdm.wrapper.hrp.serviceImpl.PatientTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class HipInitiatedServiceImpl implements HipInitiatedService {
    private static final Logger log = LogManager.getLogger(HipInitiatedServiceImpl.class);
    @Autowired
    LogsTableService logsTableService;
    @Autowired
    PatientTableService patientTableService;

    @Autowired
    PatientRepo patientRepo;
    @Autowired
    SessionManager sessionManager;

    @Autowired
    LogsRepo logsRepo;
    @Autowired
    CustomError customError;
    private WebClient.Builder webClientBuilder = WebClient.builder();
    private ResponseEntity<ObjectNode> responseEntity;

    public Object authInit(LinkRecordsResponse data) throws URISyntaxException, JsonProcessingException {
        try{
            Patients existingRecord=patientRepo.findByPatientReference(data.getPatientReference());
            if(existingRecord==null || existingRecord.getAbhaAddress()==null){
                customError.setCode(10001);
                customError.setMessage("Abha address not found with patient UID , kindly add patient");
                log.info("Patient not found");
                logsTableService.setContent(data,new HttpEntity<>(new ObjectNode(JsonNodeFactory.instance).put("Error","Patient not found, kindly add patient")), Object.class);
                return customError;
            }
            patientTableService.addPatient(data);
            HttpEntity<ObjectNode> requestEntity = AuthInitBody.builder()
                    .data(data)
                    .abhaAddress(existingRecord.getAbhaAddress())
                    .sessionManager(sessionManager)
                    .build()
                    .makeRequest();
            log.info("LinkRecords storing data");
            logsTableService.setContent(data,requestEntity, LinkRecordsResponse.class);

            responseEntity=MakeRequest.post(GatewayApiPaths.LINK_AUTH_INIT,requestEntity);

            log.info(responseEntity.getStatusCode());
            ObjectNode responseData = new ObjectNode(JsonNodeFactory.instance);
            responseData.put("Status Code : ", responseEntity.getStatusCode().toString());
            responseData.put("requestId :", ((ObjectNode)requestEntity.getBody()).get("requestId"));
            return responseData;

        }catch(Exception e){
            log.info("Link authInit : "+e);
            customError.setCode(1000);
            customError.setMessage("Error Linking careContexts");
            return customError;
        }
    }
    public ResponseEntity<ObjectNode> confirmAuth(OnInitResponse data) throws URISyntaxException, JsonProcessingException, FileNotFoundException, TimeoutException {
        RequestLogs existingRecord=logsRepo.findByGatewayRequestId1(data.getResp().getRequestId());
        if(existingRecord!=null){
            log.info("In confirmAuth found existing record");
            String otp=null;
            if(data.getAuth().getMode().equals("MOBILE_OTP")){
                log.info("confirmAuth : "+"Waiting for otp......");
                long startTime = System.currentTimeMillis();
                long timeout = TimeUnit.SECONDS.toMillis(120);
                while (otp == null) {
                    if (System.currentTimeMillis() - startTime > timeout) {
                        throw new TimeoutException("Timeout waiting for OTP in the database");
                    }
                    otp = logsRepo.findByGatewayRequestId1(data.getResp().getRequestId()).getOtp();
                }
            }
            LinkRecordsResponse linkRecordsResponse=(LinkRecordsResponse) existingRecord.getRawResponse().get("LinkRecordsResponse");
            Patients patient=patientRepo.findByPatientReference(linkRecordsResponse.getPatientReference());
            HttpEntity<ObjectNode> requestEntity = ConfirmAuthBody.builder()
                    .data(data)
                    .patients(patient)
                    .otp(otp)
                    .sessionManager(sessionManager)
                    .build()
                    .makeRequest();
            responseEntity=MakeRequest.post(GatewayApiPaths.LINK_CONFIRM_AUTH,requestEntity);
            log.info("Setting onInit response");
            logsTableService.setContent(data,requestEntity, OnInitResponse.class);
            log.info("ConfirmAuth :" + responseEntity.getStatusCode());

            return responseEntity;
        }else{
            log.info("In confirmAuth not found existing record");
        }
        return null;

    }
    public ResponseEntity<ObjectNode> addContext(OnConfirmResponse data) throws Exception {
        RequestLogs existingRecord=logsRepo.findByGatewayRequestId2(data.getResp().getRequestId());
        if(existingRecord!=null){
            log.info("in addContext found existing record");
            LinkRecordsResponse linkRecordsResponse=(LinkRecordsResponse)  existingRecord.getRawResponse().get("LinkRecordsResponse");
            Patients patient=patientRepo.findByPatientReference(linkRecordsResponse.getPatientReference());
            HttpEntity<ObjectNode> requestEntity = CareContextRequest.builder()
                    .data(data)
                    .patients(patient)
                    .careContexts(linkRecordsResponse.getPatient().getCareContexts())
                    .sessionManager(sessionManager)
                    .build()
                    .makeRequest();
            log.info("Setting onConfirm response");
            logsTableService.setContent(data,requestEntity, OnConfirmResponse.class);

            responseEntity=MakeRequest.post(GatewayApiPaths.LINK_ADD_CARE_CONTEXT,requestEntity);

            return responseEntity;
        }
        return null;
    }
}
