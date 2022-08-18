package com.notify.notify.service;


import com.notify.notify.model.request.imiApi.ImiApiChannels;
import com.notify.notify.model.request.imiApi.ImiApiDestination;
import com.notify.notify.model.request.imiApi.ImiApiRequest;
import com.notify.notify.model.request.imiApi.ImiApiSms;
import com.notify.notify.model.response.imiApi.ImiApiResponse;
import com.notify.notify.constants.imiApi.ImiApiConstants;
import com.notify.notify.model.Sms;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ImiApiCallingService {

    private final RestTemplate restTemplate;
    @Value("${imi.api-key}")
    private String apiKey;

    public ImiApiResponse call(Sms sms) {
        String url = ImiApiConstants.IMI_API_URL;
        ImiApiSms imiApiSms = ImiApiSms.builder().text(sms.getMessage()).build();
        ImiApiChannels imiApiChannels = ImiApiChannels.builder().imiApiSms(imiApiSms).build();
        ImiApiDestination imiApiDestination = ImiApiDestination.builder().msisdn(Arrays.asList(sms.getPhoneNumber())).correlationId(String.valueOf(sms.getId())).build();
        ImiApiRequest imiApiRequest = ImiApiRequest.builder().deliveryChannel(ImiApiConstants.DELIVERY_CHANNEL).imiApiChannels(imiApiChannels).destination(Arrays.asList(imiApiDestination)).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("key", apiKey);
        HttpEntity<ImiApiRequest> entity = new HttpEntity<>(imiApiRequest, headers);
        try {
            return restTemplate.exchange(
                    url, HttpMethod.POST, entity, ImiApiResponse.class).getBody();
        } catch (Exception ex) {
            log.error("Get following error while calling to imi api: {}", ex.getMessage(), ex);
            return null;
        }
    }
}
