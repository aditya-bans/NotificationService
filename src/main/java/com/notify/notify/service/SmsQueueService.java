package com.notify.notify.service;

import com.notify.notify.constants.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SmsQueueService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void queueSmsId(String messageId) {
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC_FOR_SMS, messageId);
    }
}
