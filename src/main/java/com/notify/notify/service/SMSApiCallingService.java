package com.notify.notify.service;




import com.notify.notify.model.SMS;
import com.notify.notify.model.response.ThirdPartApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class SMSApiCallingService {

        @Autowired
        private RestTemplate restTemplate;

    public ThirdPartApiResponse call(SMS sms){
            String url = "https://api.imiconnect.in/resources/v1/messaging";
            String jsonPar = "{\"deliverychannel\":\"sms\",\"channels\":{\"sms\":{\"text\":\"%s\"}},\"destination\":[{\"msisdn\":[\"%s\"],\"correlationid\":\"4\"}]}";
            String json = String.format(jsonPar, sms.getMessage(),sms.getPhoneNumber());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("key","93ceffda-5941-11ea-9da9-025282c394f2");
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            try {
                    return restTemplate.exchange(
                            url, HttpMethod.POST, entity, ThirdPartApiResponse.class).getBody();
            }
            catch (Exception ex) {
                    log.error("Get following error while calling to third party api: {}",ex.getMessage(),ex);
                    return null;
            }
    }
}
