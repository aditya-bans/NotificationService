package com.notify.notify.service;

import com.notify.notify.exceptions.InValidRequestException;
import com.notify.notify.exceptions.NotFoundException;
import com.notify.notify.model.SMS;
import com.notify.notify.model.request.SendSMSRequest;
import com.notify.notify.repository.SMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class SMSCRUDService {

    @Autowired
    private SMSRepository smsRepository;

    public Long saveSMS(SendSMSRequest sendSmsRequest)
    {
        if(sendSmsRequest.getPhoneNumber()==null||sendSmsRequest.getPhoneNumber().length()!=10)
            throw new InValidRequestException("Invalid phone number");
        SMS sms=new SMS();
        sms.setPhoneNumber(sendSmsRequest.getPhoneNumber());
        sms.setMessage(sendSmsRequest.getMessage());
        sms.setStatus("accepted");
        sms.setCreatedAt(Timestamp.from(Instant.now()));
        sms.setUpdatedAt(Timestamp.from(Instant.now()));
        sms=smsRepository.save(sms);
        return sms.getId();
    }

    public SMS getSMSById(Long smsId)
    {
        if(smsId==null)throw new InValidRequestException("Invalid request_id");
        SMS sms = smsRepository.findById(smsId).orElse(null);
        if(sms==null)
            throw new NotFoundException("request_id not found");
        return sms;
    }

    public void updateSMS(Long smsId)
    {
        SMS sms=getSMSById(smsId);
        sms.setStatus("sent");
        sms.setUpdatedAt(Timestamp.from(Instant.now()));
        smsRepository.save(sms);
    }

    public void updateSMS(Long smsId, String failureCode,String failureComments)
    {
        SMS sms=getSMSById(smsId);
        sms.setStatus("failed");
        sms.setFailureCode(failureCode);
        sms.setFailureComments(failureComments);
        sms.setUpdatedAt(Timestamp.from(Instant.now()));
        smsRepository.save(sms);
    }
}
