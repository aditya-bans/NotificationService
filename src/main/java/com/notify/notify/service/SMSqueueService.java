package com.notify.notify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SMSqueueService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void queueMessageId(String messageId)
    {
        kafkaTemplate.send("messagener",messageId);
    }
}
