package com.nha.abdm.wrapper.hrp.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component
@PropertySource({"classpath:application.properties"})
public class ApplicationConfig {
	@Value("${clientId}")
	public String clientId;
	@Value("${clientSecret}")
	public String clientSecret;

	@Value("${environment}")
	public String environment;
}