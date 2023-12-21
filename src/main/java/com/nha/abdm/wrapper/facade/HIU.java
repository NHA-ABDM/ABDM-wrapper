package com.nha.abdm.wrapper.facade;

import com.nha.abdm.wrapper.hrp.controller.HiuGateWay;
import com.nha.abdm.wrapper.hrp.responseBody.InitiateConsentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class HIU
{
    @Autowired
    HiuGateWay hiuGateWay;
    @PostMapping("/initiateConsent")
    public String HiuConsentRequestInit(@RequestBody InitiateConsentResponse data) throws URISyntaxException, JsonProcessingException {
        return hiuGateWay.initiateConsent(data);

    }


}
