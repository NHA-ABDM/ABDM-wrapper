package com.nha.abdm.wrapper.hrp.controller;

import com.nha.abdm.wrapper.hrp.services.CommonServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;


@RestController
public class ApiController {
    @Autowired
    CommonServices commonServices;
    private static final Logger log = LogManager.getLogger(ApiController.class);
    @PostMapping("/session")
    public String startSession() throws URISyntaxException, JsonProcessingException {
        return(commonServices.startSession());

    }

}
