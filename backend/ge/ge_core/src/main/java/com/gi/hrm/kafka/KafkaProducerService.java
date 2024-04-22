package com.gi.hrm.kafka;

import lombok.NonNull;

public interface KafkaProducerService {
    void sendMessage(@NonNull String topic, String key, String message);
}
