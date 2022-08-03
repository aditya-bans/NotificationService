package com.notify.notify.service;

import com.notify.notify.model.Message;
import com.notify.notify.model.SMS;
import com.notify.notify.model.response.ThirdPartApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SMSProcessService {

    @Autowired
    private SMSCRUDService smsCRUDService;

    @Autowired
    private BlackListNumbersService blackListNumbersService;

    @Autowired
    private SMSApiCallingService smsApiCallingService;

    @Autowired
    private SMSSearchService smsSearchService;


    @KafkaListener(topics = "messagener", groupId = "groupId")
    public void listener(String smsId)
    {
        Long integerSmsId = Long.parseLong(smsId);
        SMS sms= smsCRUDService.getSMSById(integerSmsId);
        if(blackListNumbersService.isExist(sms.getPhoneNumber()))
        {
            smsCRUDService.updateSMS(integerSmsId,"401","blacklisted number");
            return;
        }
        ThirdPartApiResponse response = smsApiCallingService.call(sms);
        log.info("Response from third party api: {}",response);
        if(!response.getResponse().get(0).getCode().equals("1001"))
        {
            smsCRUDService.updateSMS(integerSmsId,"401","blacklisted number");
            return;
        }
        smsCRUDService.updateSMS(integerSmsId);
        Message message = new Message(smsId,sms.getPhoneNumber(),sms.getMessage());
        smsSearchService.indexMessage(message);
        return;
    }
}
