package com.nha.abdm.wrapper.hrp.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;

public interface CommonServices {
    String startSession() throws JsonProcessingException, URISyntaxException;
}
