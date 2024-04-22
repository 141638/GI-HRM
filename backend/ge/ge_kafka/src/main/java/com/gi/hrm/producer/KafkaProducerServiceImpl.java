package com.gi.hrm.producer;

import com.gi.hrm.kafka.KafkaProducerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    /** kafka template */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * service for sending message to a specific topic in kafka with default key
     * @param topic   topic
     * @param message message
     */
    public void sendMessage(@NonNull String topic, String message) {
        this.sendMessage(topic, "testTopic", message);
    }

    /**
     * service for sending message to a specific topic in kafka
     * @param topic   topic
     * @param key     key
     * @param message message
     */
    public void sendMessage(@NonNull String topic, String key, String message) {
        // TODO: topc/message validate(maybe)

        CompletableFuture<SendResult<String, String>> futureSendResult = kafkaTemplate.send(topic, key, message);
        futureSendResult.whenComplete((result, error) -> {
            if (Objects.isNull(error)) {
                log.info("PRODUCER: message sent successfully to topic=[{}], key=[{}] message=[{}] with offset=[{}]", topic, key, message,
                    result.getRecordMetadata().offset());
            } else {
                log.error("PRODUCER: failed to send message to topic=[{}], error=[{}]", topic, error.getMessage());
            }
        });
    }
}
