package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.ValueObject;
import com.gi.hrm.mongo.MongoUtilService;
import reactor.core.publisher.Mono;

import static com.gi.hrm.task.command.domain.Category.SEQUENCE_NAME;

public record TaskId(Integer value) implements ValueObject {

    public static Mono<TaskId> create(MongoUtilService mongoService) {
        return mongoService.generateSequence(SEQUENCE_NAME).map(TaskId::new);
    }
}
