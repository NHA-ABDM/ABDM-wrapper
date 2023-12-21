package com.nha.abdm.wrapper.hrp.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:application.properties"})
public class ApplicationConfig {
    @Value("${createSession}")
   public String url;
    @Value("${clientId}")
    public String clientId;

    @Value("${clientSecret}")
    public String clientSecret;

    @Value("${consent_init}")
    public String initApi;

    @Value("${onNotify}")
    public String onNotify;
    @Value("${fetch}")
    public String fetch;

    @Value("${environment}")
    public String environment;


}
