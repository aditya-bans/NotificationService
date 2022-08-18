package com.notify.notify.service;

import com.notify.notify.model.Sms;
import com.notify.notify.model.response.imiApi.ImiApiResponse;
import com.notify.notify.constants.kafka.KafkaConstants;
import com.notify.notify.constants.utility.UtilityConstants;
import com.notify.notify.model.SmsDataForElasticsearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SmsProcessService {

    private final SmsCRUDService smsCRUDService;

    private final BlackListNumbersService blackListNumbersService;

    private final ImiApiCallingService imiApiCallingService;

    private final SmsSearchService smsSearchService;


    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC_FOR_SMS, groupId = KafkaConstants.KAFKA_GROUP_ID)
    public void listener(String smsId) {
        Long integerSmsId = Long.parseLong(smsId);
        Sms sms = smsCRUDService.getSMSById(integerSmsId);
        if (blackListNumbersService.isExist(sms.getPhoneNumber())) {
            smsCRUDService.updateSMS(integerSmsId,UtilityConstants.CODE_401, UtilityConstants.BLACKLISTED_NUMBER);
            return;
        }
        ImiApiResponse response = imiApiCallingService.call(sms);
        log.info("Response from third party api: {}", response);
        if (ObjectUtils.isEmpty(response) || !response.getResponse().get(0).getCode().equals(UtilityConstants.CODE_1001)) {
            smsCRUDService.updateSMS(integerSmsId, UtilityConstants.CODE_401, UtilityConstants.IMI_API_FAILED_TO_SEND_SMS);
            return;
        }
        smsCRUDService.updateSMS(integerSmsId);
        SmsDataForElasticsearch smsDataForElasticsearch = SmsDataForElasticsearch.builder().id(smsId).phoneNumber(sms.getPhoneNumber()).message(sms.getMessage()).time(LocalDateTime.now()).build();
        smsSearchService.indexMessage(smsDataForElasticsearch);
        return;
    }
}
