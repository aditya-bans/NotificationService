package com.notify.notify.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.connect.time-out}")
    private Integer connectTimeOut;
    @Value("${resttemplate.read.time-out}")
    private Integer readTimeOut;
    @Bean
    public RestTemplate customRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(connectTimeOut);
        httpRequestFactory.setReadTimeout(readTimeOut);
        return new RestTemplate(httpRequestFactory);
    }
}
