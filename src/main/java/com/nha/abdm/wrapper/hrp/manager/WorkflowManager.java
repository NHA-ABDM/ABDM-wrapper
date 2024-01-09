package com.nha.abdm.wrapper.hrp.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nha.abdm.wrapper.hrp.CommonHelpers.ResponseHelper;
import com.nha.abdm.wrapper.hrp.CommonHelpers.VerifyOtp;
import com.nha.abdm.wrapper.hrp.common.SessionManager;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.InitResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.services.DiscoverLinkingService;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.AddPatient;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.services.HipInitiatedService;
import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import com.nha.abdm.wrapper.hrp.repository.PatientRepo;
import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import com.nha.abdm.wrapper.hrp.serviceImpl.PatientTableService;
import com.nha.abdm.wrapper.hrp.services.CommonServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class WorkflowManager {
	private static final Logger log = LogManager.getLogger(WorkflowManager.class);
	@Autowired
	@Lazy
	DiscoverLinkingService discoverLinkingService;
	@Autowired
	PatientTableService patientTableService;
	@Autowired
	LogsTableService logsTableService;
	@Autowired
	HipInitiatedService hipInitiatedService;

	@Autowired
	PatientRepo patientRepo;
	@Autowired
	SessionManager sessionManager;
	@Autowired
	CommonServices commonServices;
	public Object linkRecords(@Validated @RequestBody LinkRecordsResponse data) throws JsonProcessingException, URISyntaxException, FileNotFoundException, ParseException, ParseException {
		return hipInitiatedService.authInit(data);
	}


	public void startConfirmCall(@RequestBody OnInitResponse data) throws FileNotFoundException, URISyntaxException, JsonProcessingException, TimeoutException {
			if(data!=null && data.getError()==null){
				ResponseEntity<ObjectNode> confirmCall = hipInitiatedService.confirmAuth(data);
			}else if(data.getError()!=null){
				log.error("OnInit error"+ data.getError().getMessage());
			}else{
				log.error("Oninit -> error due to response");
			}

	}

	public void startAddCareContextCall(OnConfirmResponse data) throws Exception {
		if(data!=null && data.getError()==null){
			ResponseEntity<ObjectNode> addCareContextCAll = hipInitiatedService.addContext(data);
		}else if(data.getError()!=null){
			log.error("OnConfirm error"+ data.getError().getMessage());
		}else{
			log.error("OnConfirm -> error due to response");
		}
	}
	public void startOnDiscoverCall(DiscoverResponse data) throws URISyntaxException, JsonProcessingException {
		if(data!=null){
			discoverLinkingService.onDiscoverCall(data);
		}else{
			log.error("Error in Discover response from gateWay");
		}
	}

	public void startOnInitCall(InitResponse data) throws URISyntaxException, JsonProcessingException {
		if(data!=null){
			discoverLinkingService.onInitCall(data);
		}else{
			log.error("Error in Init response from gateWay");
		}
	}

	public void startOnConfirmCall(ConfirmResponse data) throws URISyntaxException, JsonProcessingException {
		if(data!=null){
			discoverLinkingService.onConfirmCall(data);
		}else{
			log.error("Error in Confirm response from gateWay");
		}
	}
	public String getCareContextRequestStatus(ResponseHelper data) {
		return logsTableService.getStatus(data);
	}

	public String storePatientInWrapper(AddPatient data){
			return patientTableService.addPatient(data);
	}
	public void storeOtp(VerifyOtp data){
		logsTableService.setOtp(data);
	}
}
