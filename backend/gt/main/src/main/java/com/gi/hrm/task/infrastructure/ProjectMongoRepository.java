package com.gi.hrm.task.infrastructure;

import com.gi.hrm.command.domain.EntityState;
import com.gi.hrm.exception.RecordNotFoundException;
import com.gi.hrm.task.command.domain.Project;
import com.gi.hrm.task.command.domain.ProjectId;
import com.gi.hrm.task.command.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProjectMongoRepository implements ProjectRepository {

    private final ProjectMapper projectMapper;
    private final CategoryMapper categoryMapper;
    private final ReactiveMongoTransactionManager transactionManager;

    @Override
    public Mono<Project> findById(ProjectId id) {
        return projectMapper.findById(id.value())
                .flatMap(this::getCategoriesToProject);
    }

    @Override
    public List<Project> findAll() {
        return List.of();
    }

    private Mono<Project> getCategoriesToProject(ProjectRecord projectRecord) {
        return categoryMapper.findByProjectId(projectRecord.id()).collectList().map(projectRecord::toDomain);
    }

    @Override
    public Mono<Project> get(ProjectId id) {
        return projectMapper.findById(id.value())
                .switchIfEmpty(Mono.error(new RecordNotFoundException("No project found with value: " + id.value())))
                .flatMap(this::getCategoriesToProject);
    }

    @Override
    public Mono<Project> save(Project aggregateRoot) {
        return switch (aggregateRoot.getAggregateState()) {
            case EntityState.Created ignored -> create(aggregateRoot);
            case EntityState.Dirty ignored -> update(aggregateRoot);
            case EntityState.Deleted ignored -> delete(aggregateRoot);
            case EntityState.Persisted ignored -> Mono.just(aggregateRoot);
        };
    }

    private Mono<Project> create(Project project) {
        // reactive transactional operator
        val rxtx = TransactionalOperator.create(transactionManager);

        // project record
        val projectRecord = ProjectRecord.fromDomain(project);

        return projectMapper.save(projectRecord)
                .onErrorResume(e -> {
                    log.error("Failed to save project record", e);
                    return Mono.error(new IllegalStateException("Error occurred inserting project", e));
                })
                .flatMapMany(saved -> replaceCategories(project))
                .then(Mono.just(project.persist()))
                .as(rxtx::transactional);
    }

    private Mono<Project> update(Project project) {
        // reactive transactional operator
        val rxtx = TransactionalOperator.create(transactionManager);

        // project record
        val projectRecord = ProjectRecord.fromDomain(project);

        return projectMapper.save(projectRecord)
                .onErrorResume(e -> {
                    log.error("Failed to save project record", e);
                    return Mono.error(new IllegalStateException("Error occurred inserting project", e));
                })
                .flatMapMany(saved -> replaceCategories(project))
                .then(Mono.just(project.persist()))
                .as(rxtx::transactional);
    }

    private Mono<Project> delete(Project project) {
        // reactive transactional operator
        final TransactionalOperator rxtx = TransactionalOperator.create(transactionManager);
        // retrieve projectId raw value
        final Integer projectId = project.id().value();
        // delete categories
        final Mono<Void> deleteCategoriesByProjectId = categoryMapper.deleteByProjectId(projectId);
        // delete project
        final Mono<Void> deleteProjectById = projectMapper.deleteById(projectId);

        return deleteCategoriesByProjectId
                .onErrorResume(e -> {
                    log.error("Failed to delete categories for projectId={}", projectId, e);
                    return Mono.error(new IllegalStateException("Error occurred when deleting categories", e));
                })
                .then(deleteProjectById)
                .onErrorResume(e -> {
                    log.error("Failed to delete project for id={}", projectId, e);
                    return Mono.error(new IllegalStateException("Error occurred when deleting project", e));
                })
                .thenReturn(project).as(rxtx::transactional);
    }

    private Flux<CategoryRecord> replaceCategories(Project project) {
        // get project ID
        val projectId = project.id();

        // delete action
        val deleteByProjectIdMono = categoryMapper.deleteByProjectId(projectId.value());

        // map the domain record to the persistence record
        val categoryRecords = Flux.fromIterable(project.categories().getValue())
                .map(category -> CategoryRecord.fromDomain(category, projectId)).collectList();

        // insert action
        val insertNewCategoryFlux = categoryRecords
                .doOnSuccess(categoryRecord -> log.info("{}", categoryRecord.toString()))
                .flatMapMany(categoryMapper::saveAll);

        return deleteByProjectIdMono
                .onErrorResume(e -> {
                    log.error("Failed to delete category records by projectId", e);
                    return Mono.error(new IllegalStateException("Error occurred deleting categories", e));
                })
                .thenMany(insertNewCategoryFlux)
                .onErrorResume(e -> {
                    log.error("Failed to save category records", e);
                    return Mono.error(new IllegalStateException("Error occurred importing category records", e));
                });
    }
}
