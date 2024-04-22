package com.gi.hrm.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    /** bootstrap address config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    /** group id config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.group-id}")
    private String groupId;

    /** enable auto commit config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.enable-auto-commit}")
    private Boolean enableAutoCommit;

    /** interval auto commit config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.interval-auto-commit}")
    private String intervalAutoCommit;

    /** session timeout config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.session-time-out}")
    private String sessionTimeOut;

    /** auto offset reset config */
    @Value(value = "${com.gi.hrm.config.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    /**
     * create a new consumer factory with the custom configurations
     *
     * @return new consumer factory
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configPrps = new HashMap<>();
        configPrps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configPrps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configPrps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configPrps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configPrps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        configPrps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, intervalAutoCommit);
        configPrps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeOut);
        configPrps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        return new DefaultKafkaConsumerFactory<>(configPrps);
    }

    /**
     * create a new kafka listener container factory using the configured consumer factory
     *
     * @return new kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(consumerFactory());

        return listenerFactory;
    }
}
