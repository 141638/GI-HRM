package com.gi.hrm.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    @KafkaListener(
        topics = "${com.gi.hrm.config.kafka.topic.test-topic}",
        groupId = "${com.gi.hrm.config.kafka.consumer.group-id}")
    public void testTopicListener(
        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header(required = false, name = KafkaHeaders.RECEIVED_KEY) String key,
        String message) {
        // logout the received message
        log.info("CONSUMER: topic[{}] {}={}", topic, key, message);
    }
}
