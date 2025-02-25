package com.gi.hrm.task.infrastructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryMapper extends ReactiveMongoRepository<CategoryRecord, Integer> {

    Flux<CategoryRecord> findByProjectId(Integer projectId);

    Mono<Void> deleteByProjectId(Integer projectId);
}
