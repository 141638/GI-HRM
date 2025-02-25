package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.ValueObject;
import com.gi.hrm.mongo.MongoUtilService;
import reactor.core.publisher.Mono;

import static com.gi.hrm.task.command.domain.Project.SEQUENCE_NAME;


public record ProjectId(Integer value) implements ValueObject {

    public static Mono<ProjectId> create(MongoUtilService mongoService) {
        return mongoService.generateSequence(SEQUENCE_NAME).map(ProjectId::new);
    }
}
