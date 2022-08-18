package com.notify.notify.service;

import com.notify.notify.exceptions.NotFoundException;
import com.notify.notify.repository.SmsRepository;
import com.notify.notify.constants.utility.UtilityConstants;
import com.notify.notify.enums.SmsStatus;
import com.notify.notify.model.Sms;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SmsCRUDService {

    private final SmsRepository smsRepository;

    public Sms saveSms(Sms sms) {
        return smsRepository.save(sms);
    }

    public Sms getSMSById(Long smsId) {
        Sms sms = smsRepository.findById(smsId).orElse(null);
        if (ObjectUtils.isEmpty(sms))
            throw new NotFoundException(UtilityConstants.REQUEST_ID_NOT_FOUND);
        return sms;
    }

    public void updateSMS(Long smsId) {
        Sms sms = getSMSById(smsId);
        sms.setStatus(SmsStatus.SENT);
        smsRepository.save(sms);
    }

    public void updateSMS(Long smsId, String failureCode, String failureComments) {
        Sms sms = getSMSById(smsId);
        sms.setStatus(SmsStatus.FAILED);
        sms.setFailureCode(failureCode);
        sms.setFailureComments(failureComments);
        smsRepository.save(sms);
    }
}
