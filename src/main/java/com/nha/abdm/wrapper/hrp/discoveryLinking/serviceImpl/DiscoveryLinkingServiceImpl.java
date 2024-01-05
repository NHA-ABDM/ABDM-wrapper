package com.nha.abdm.wrapper.hrp.discoveryLinking.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.common.*;
import com.nha.abdm.wrapper.hrp.discoveryLinking.requests.OnConfirmRequest;
import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import com.nha.abdm.wrapper.hrp.repository.LogsRepo;
import com.nha.abdm.wrapper.hrp.repository.PatientRepo;
import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;
import com.nha.abdm.wrapper.hrp.discoveryLinking.requests.OnDiscoverRequest;
import com.nha.abdm.wrapper.hrp.discoveryLinking.requests.OnInitRequest;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.InitResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.services.DiscoverLinkingService;
import com.nha.abdm.wrapper.hrp.serviceImpl.PatientTableService;
import info.debatty.java.stringsimilarity.JaroWinkler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.lang.String;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscoveryLinkingServiceImpl implements DiscoverLinkingService {
    private static final Logger log = LogManager.getLogger(DiscoverLinkingService.class);
    @Autowired
    SessionManager sessionManager;
    @Autowired
    PatientTableService patientTableService;
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    Utils utils;
    @Autowired
    LogsRepo logsRepo;

    @Autowired
    LogsTableService logsTableService;
    CustomError customError=new CustomError();
    JaroWinkler jaroWinkler = new JaroWinkler();
    ResponseEntity<ObjectNode> responseEntity;
    public void onDiscoverCall(DiscoverResponse data) throws URISyntaxException, JsonProcessingException {
        String abhaAddress = data.getPatient().getId();
        log.info("AbhaAddress: " + abhaAddress);
        try{
            Patients isAbhaPresent=patientRepo.findByAbhaAddress(abhaAddress);
            Patients isMobilePresent=patientRepo.findByPatientMobile(data.getPatient().getVerifiedIdentifiers().get(0).getValue());
            Patients isPatientIdentifier=null;
            if(!data.getPatient().getUnverifiedIdentifiers().isEmpty()) isPatientIdentifier=patientRepo.findByPatientReference(data.getPatient().getUnverifiedIdentifiers().get(0).getValue());
            if(Objects.nonNull(isAbhaPresent)){
                log.info("Patient matched with AbhaAddress");
                List<CareContextBuilder> careContexts = isAbhaPresent.getCareContexts().stream()
                        .filter(context -> !context.isLinked())
                        .collect(Collectors.toList());
                makeBody(data,isAbhaPresent,careContexts);
            }else if(Objects.nonNull(isMobilePresent)){
                log.info("Patient matched with Mobile");
                String existingGender=isMobilePresent.getGender();
                int existingDate=Integer.parseInt(isMobilePresent.getDateOfBirth().substring(0,4));
                log.info(existingDate +" given yob : "+data.getPatient().getYearOfBirth()+ " difference : "+ Math.abs(existingDate-Integer.parseInt(data.getPatient().getYearOfBirth()))+" name :"+ jaroWinkler.similarity(isMobilePresent.getName(),data.getPatient().getName()));
                if(existingGender.equals(data.getPatient().getGender()) && Math.abs(existingDate-Integer.parseInt(data.getPatient().getYearOfBirth()))<=5 && jaroWinkler.similarity(isMobilePresent.getName(),data.getPatient().getName())>=0.5){//&& soundex.encode(isMobilePresent.getName()).equals(data.getPatient().getName())
                    List<CareContextBuilder> careContexts = isMobilePresent.getCareContexts().stream()
                            .filter(context -> !context.isLinked())
                            .collect(Collectors.toList());
                    makeBody(data,isMobilePresent,careContexts);

                }else{
                    customError.setCode(1000);
                    customError.setMessage("HIP -> Details mismatch with mobile");
                    noPatient(data,customError);
                }
            }else if(Objects.nonNull(isPatientIdentifier)){
                log.info("Patient matched with Patient Identifier");
                String existingGender=isPatientIdentifier.getGender();
                int existingDate=Integer.parseInt(isPatientIdentifier.getDateOfBirth().substring(0,4));
                log.info(existingDate +" given yob : "+data.getPatient().getYearOfBirth()+ " difference : "+ Math.abs(existingDate-Integer.parseInt(data.getPatient().getYearOfBirth()))+" name :"+ jaroWinkler.similarity(isPatientIdentifier.getName(),data.getPatient().getName()));
                if(existingGender.equals(data.getPatient().getGender()) && Math.abs(existingDate-Integer.parseInt(data.getPatient().getYearOfBirth()))<=5&& jaroWinkler.similarity(isPatientIdentifier.getName(),data.getPatient().getName())>=0.5) {// && soundex.encode(isMobilePresent.getName()).equals(data.getPatient().getName()
                    List<CareContextBuilder> careContexts = isPatientIdentifier.getCareContexts().stream()
                            .filter(context -> !context.isLinked())
                            .collect(Collectors.toList());
                    makeBody(data, isPatientIdentifier, careContexts);
                }else{
                    customError.setCode(1000);
                    customError.setMessage("HIP : Details mismatch with patientIdentifier");
                    noPatient(data,customError);
                }
            }else {
                customError.setCode(1000);
                customError.setMessage("HIP -> Patient Not found");
                noPatient(data,customError);
            }
        }catch(Exception e){
            log.error("OnDiscover : "+ Arrays.toString(e.getStackTrace()));
        }

    }
    public void makeBody(DiscoverResponse data,Patients patients,List<CareContextBuilder> careContexts) throws URISyntaxException, JsonProcessingException {
        String abhaAddress=data.getPatient().getId();
        String patientMobile= patients.getPatientMobile();
        String referenceNumber= patients.getPatientReference();
        String display=patients.getDisplay();
        HttpEntity<ObjectNode> requestEntity = OnDiscoverRequest.builder()
                .data(data)
                .abhaAddress(abhaAddress)
                .referenceNumber(referenceNumber)
                .display(display)
                .careContexts(careContexts)
                .sessionManager(sessionManager)
                .build()
                .makeRequest();
        try {
            ResponseEntity<ObjectNode> responseEntity=MakeRequest.post(GatewayApiPaths.ON_DISCOVER,requestEntity);
            log.info(GatewayApiPaths.ON_DISCOVER+" : onDiscoverCall: " + responseEntity.getStatusCode());
            logsTableService.setContent(data,requestEntity, DiscoverResponse.class);
        } catch (Exception e) {
            log.info("Error: " + e);
        }
    }
    public void noPatient(DiscoverResponse data,CustomError customError){
        try {
            HttpEntity<ObjectNode> requestEntity = OnDiscoverRequest.builder()
                    .data(data)
                    .abhaAddress(data.getPatient().getId())
                    .referenceNumber(null)
                    .display(null)
                    .careContexts(Collections.emptyList())
                    .sessionManager(sessionManager)
                    .customError(customError)
                    .build()
                    .makeRequest();
            ResponseEntity<ObjectNode> responseEntity=MakeRequest.post(GatewayApiPaths.ON_DISCOVER,requestEntity);

            log.info("Discover: requestId : " + data.getRequestId() + ": Patient not found");
        } catch (Exception e) {
            log.info(e);
        }
    }
    public void onInitCall(InitResponse data) throws URISyntaxException, JsonProcessingException {
        boolean isCareContextPresent=patientTableService.checkCareContexts(data);
        log.info("status : "+isCareContextPresent);
        HttpEntity<ObjectNode> requestEntity=null;
        if(isCareContextPresent) {
            requestEntity = OnInitRequest.builder()
                    .data(data)
                    .customError(new CustomError())
                    .sessionManager(sessionManager)
                    .build().makeRequest();
        }else {
            CustomError customError=new CustomError();
            customError.setMessage("HIP -> Mismatch of careContext");
            customError.setCode(1000);
            log.error("OnInit body -> making error body since careContexts are not matched");
            requestEntity = OnInitRequest.builder()
                    .data(data)
                    .sessionManager(sessionManager)
                    .customError(customError)
                    .build().makeRequest();
        }
        try{
            ResponseEntity<ObjectNode> responseEntity=MakeRequest.post(GatewayApiPaths.ON_INIT,requestEntity);
            log.info(GatewayApiPaths.ON_INIT+" : onInitCall: " + responseEntity.getStatusCode());
        }catch(Exception e){
            log.info(GatewayApiPaths.ON_INIT+" : OnInitCall -> Error : "+Arrays.toString(e.getStackTrace()));
        }
        try{
            logsTableService.setContent(data,requestEntity, InitResponse.class);
        }catch(Exception e){
            log.info("onInitCall -> Error: unable to set content : "+Arrays.toString(e.getStackTrace()));
        }
    }



    public void onConfirmCall(ConfirmResponse data) throws URISyntaxException, JsonProcessingException {
        List<CareContextBuilder> careContexts=null;
        String referenceNumber=null;
        String display=null;
        String linkRefNumber=data.getConfirmation().getLinkRefNumber();
        log.info("LinkRefNumber : "+linkRefNumber);
        String abhaAddress=logsTableService.getPatientId(linkRefNumber);
        String patientReference= logsTableService.getPatientReference(linkRefNumber);
        Patients patientWithAbha=patientRepo.findByAbhaAddress(abhaAddress);
        Patients patientWithPatientRef=patientRepo.findByPatientReference(patientReference);
        if(patientWithAbha!=null){
            careContexts=patientWithAbha.getCareContexts();
            display=patientWithAbha.getDisplay();
        }else if(patientWithPatientRef!=null){
            careContexts=patientWithPatientRef.getCareContexts();
            display=patientWithPatientRef.getDisplay();
        }
        log.info("onConfirm: Abha= "+abhaAddress);
        if(abhaAddress==null){
            log.info("OnconfirmCall -> patient with abhaAddress not found in logs :"+ abhaAddress);
        }
        List<CareContextBuilder> selectedCareContexts = logsTableService.getSelectedCareContexts(linkRefNumber,careContexts);
        try {
        HttpEntity<ObjectNode> requestEntity = OnConfirmRequest.builder()
                .data(data)
                .abhaAddress(abhaAddress)
                .referenceNumber(patientReference)
                .display(display)
                .careContexts(selectedCareContexts)
                .linkRefNumber(linkRefNumber)
                .sessionManager(sessionManager)
                .build()
                .makeRequest();

            ResponseEntity<ObjectNode> responseEntity=MakeRequest.post(GatewayApiPaths.ON_CONFIRM,requestEntity);
        log.info(GatewayApiPaths.ON_CONFIRM+" : onConfirmCall: " + responseEntity.getStatusCode());
        patientTableService.updateCareContextStatus(patientReference,selectedCareContexts);
        }
        catch (Exception e){
            log.error(GatewayApiPaths.ON_CONFIRM+" : OnConfirmCall -> Error :"+ Arrays.toString(e.getStackTrace()));
        }
    }
}