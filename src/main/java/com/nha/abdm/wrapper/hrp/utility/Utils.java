package com.nha.abdm.wrapper.hrp.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nha.abdm.wrapper.hrp.properties.ApplicationConfig;
import com.nha.abdm.wrapper.hrp.services.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;

@Component
public class Utils {
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    CommonServices commonServices;
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken =accessToken;
    }

    public String accessToken;

    public String fetchAccessToken() throws URISyntaxException, JsonProcessingException {
        if(accessToken==null){
            return commonServices.startSession();
        }
        return getAccessToken();
    }

        public Date getCurrentTimeStamp() {
            return Date.from(Instant.now());
        }
    public HttpHeaders initialiseHeadersForGateway() throws URISyntaxException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-CM-ID", applicationConfig.environment);
        headers.add("Authorization",  fetchAccessToken());
        return headers;
    }

}
