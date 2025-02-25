package com.gi.hrm.task.infrastructure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProjectMapper extends ReactiveMongoRepository<ProjectRecord, Integer> {

    Flux<ProjectRecord> findAllBy(Pageable pageable);
}
