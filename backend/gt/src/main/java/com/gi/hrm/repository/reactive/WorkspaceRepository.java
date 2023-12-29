package com.gi.hrm.repository.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.gi.hrm.entity.Workspace;
import com.gi.hrm.repository.template.workspace.WorkspaceTemplateOperation;

import reactor.core.publisher.Mono;

@Repository
public interface WorkspaceRepository extends ReactiveMongoRepository<Workspace, Integer>, WorkspaceTemplateOperation {
	Mono<Workspace> findByIdAndDeleteFlagFalse(Integer id);
}
