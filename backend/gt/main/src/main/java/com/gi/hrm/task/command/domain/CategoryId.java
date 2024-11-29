package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.ValueObject;
import com.gi.hrm.mongo.MongoUtilService;
import reactor.core.publisher.Mono;

import static com.gi.hrm.task.command.domain.Category.SEQUENCE_NAME;

public record CategoryId(Integer id) implements ValueObject {
    public static Mono<CategoryId> create(MongoUtilService mongoService) {
        return mongoService.generateSequence(SEQUENCE_NAME).map(CategoryId::new);
    }
}
