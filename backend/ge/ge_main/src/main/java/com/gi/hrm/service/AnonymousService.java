package com.gi.hrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnonymousService {
    /** service for handling kafka producing task */
//    @Autowired
//    private KafkaProducerService kafkaProducerService;

    /** test topic */
//    @Value(value = "${com.gi.hrm.config.kafka.topic.test-topic}")
    private String testTopic;

    /**
     * method used for sending message to kafka test topic
     * @param message message
     */
    public void kafkaTestTopicSender(String key, String message) {
//        kafkaProducerService.sendMessage(testTopic, key, message);
    }
}
