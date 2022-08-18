package com.notify.notify.service;

import com.notify.notify.model.request.SendSmsRequest;
import com.notify.notify.enums.SmsStatus;
import com.notify.notify.model.Sms;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SendSmsService {
    private final SmsCRUDService smsCRUDService;
    private final SmsQueueService smsQueueService;

    public String sendSms(SendSmsRequest sendSmsRequest)
    {
        Sms sms = new Sms();
        sms.setPhoneNumber(sendSmsRequest.getPhoneNumber());
        sms.setMessage(sendSmsRequest.getMessage());
        sms.setStatus(SmsStatus.QUEUED);
        sms= smsCRUDService.saveSms(sms);
        Long messageId =sms.getId();
        String stringMessageId = String.valueOf(messageId);
        smsQueueService.queueSmsId(stringMessageId);
        return stringMessageId;
    }
}
