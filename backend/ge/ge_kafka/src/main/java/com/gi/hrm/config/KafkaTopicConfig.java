package com.gi.hrm.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka address and topic configuration
 */
@Configuration
public class KafkaTopicConfig {
    /** bootstrap address config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${com.gi.hrm.config.kafka.topic.test-topic}")
    private String testTopic;

    /**
     * create new kafka admin with predefined configuration
     *
     * @return KafkaAdmin
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    /**
     * create the test topic
     *
     * @return the test topic
     */
    @Bean
    public NewTopic testTopic() {
        return new NewTopic(testTopic, 1, (short) 1);
    }

}
