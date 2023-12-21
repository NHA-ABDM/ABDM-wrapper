package com.nha.abdm.wrapper.hrp.responseController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nha.abdm.wrapper.hrp.controller.HiuGateWay;
import com.nha.abdm.wrapper.hrp.responseBody.NotifyResponse;
import com.nha.abdm.wrapper.hrp.responseBody.OnFetchBody;
import com.nha.abdm.wrapper.hrp.responseBody.OnInitResponse;
import com.nha.abdm.wrapper.hrp.serviceImpl.LogsTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class ResponseData {
    @Autowired
    HiuGateWay hiuGateWay;

    @Autowired
    LogsTableService logsTableService;
    private static final Logger log = LogManager.getLogger(ResponseData.class);
    @PostMapping("/v0.5/consent-requests/on-init")
    public void onInit (@RequestBody OnInitResponse data) {
        log.info(data.printData());
        logsTableService.setConsentId(data);

    }
//    @PostMapping("/v0.5/users/auth/on-fetch-modes")
//    public void fetchModes(@RequestBody String data) {
//        log.info("Got fetch modes");
//
//    }
    @PostMapping("/v0.5/consents/hiu/notify")
    public void notify(@RequestBody NotifyResponse data) throws URISyntaxException, JsonProcessingException {
        hiuGateWay.startOnNotify(data);
        int ArtifectsSize= data.getNotification().getConsentArtefacts().size();
        for(int i=0;i<ArtifectsSize;i++){
            hiuGateWay.startFetch(data,data.getNotification().getConsentArtefacts(),i);
        log.info("Success");
        }
    }
    @PostMapping("/v0.5/consents/hiu/onFetch")
    public void notify(@RequestBody OnFetchBody data){
       hiuGateWay.startRequest(data);
    }
}
