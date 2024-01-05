package com.nha.abdm.wrapper.hrp.gatewayController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.ConfirmResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.InitResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnAddCareContextResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnInitResponse;
import com.nha.abdm.wrapper.hrp.manager.WorkflowManager;

import java.io.IOException;
import java.net.URISyntaxException;

import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class GatewayCallbackController {

	@Autowired
	WorkflowManager workflowManager;
	@Autowired
	LogsTableService logsTableService;

	private static final Logger log = LogManager.getLogger(GatewayCallbackController.class);

	@PostMapping({"/v0.5/users/auth/on-init"})
	public void onInitResponse(@RequestBody OnInitResponse data) throws IOException, URISyntaxException {
		log.info("getError in OnInitRequest callback: " + data.getError());
		if (data != null && data.getError() == null) {
			log.info(data.toString());
			log.info(data.getAuth().getTransactionId());
			this.workflowManager.startConfirmCall(data);
		} else {
			log.info("Error in onInitCall: " + data.getError());
		}

	}

	@PostMapping({"/v0.5/users/auth/on-confirm"})
	public void onConfirmCall(@RequestBody OnConfirmResponse data) throws Exception {
		if (data != null && data.getError() == null) {
			log.info(data.toString());
			log.info("onConfirm : " + data.getAuth().getAccessToken());
			log.info("starting to add CareContext");
			this.workflowManager.startAddCareContextCall(data);
		}else log.info("failed in on-confirm");
	}

	@PostMapping({"/v0.5/links/link/on-add-contexts"})
	public void onAddCareContext(@RequestBody OnAddCareContextResponse data) {
		if (data != null && data.getError() == null) {
			log.info("Linked CareContext STATUS :" + data.getAcknowledgement().getStatus());
			logsTableService.setStatus(data);
		} else {
			log.info("Failed to add Context");
		}

	}
	@PostMapping("/v0.5/care-contexts/discover")
	public void discoverCall(@RequestBody DiscoverResponse data) throws URISyntaxException, JsonProcessingException {
		if(data!=null && data.getError()==null){
			log.info("/v0.5/care-contexts/discover :" +data.toString());
			workflowManager.startOnDiscoverCall(data);
		}else{
			log.error("/v0.5/care-contexts/discover :" +data.getError().getMessage());
		}
	}
	@PostMapping("/v0.5/links/link/init")
	public void initCall(@RequestBody InitResponse data) throws URISyntaxException, JsonProcessingException {
		if(data!=null && data.getError()==null){
			log.info("/v0.5/links/link/init :"+data.toString());
			workflowManager.startOnInitCall(data);
		}else{
			log.error("/v0.5/links/link/init :"+data.getError().getMessage());
		}
	}
	@PostMapping("/v0.5/links/link/confirm")
	public void confirmCall(@RequestBody ConfirmResponse data) throws URISyntaxException, JsonProcessingException {
		if(data!=null && data.getError()==null){
			log.info("/v0.5/links/link/confirm : "+data.toString());
			workflowManager.startOnConfirmCall(data);
		}else{
			log.error("/v0.5/links/link/confirm : "+data.getError().getMessage());
		}
	}
}
