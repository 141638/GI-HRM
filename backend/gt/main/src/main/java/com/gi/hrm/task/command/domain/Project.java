package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.AggregateRoot;
import com.gi.hrm.command.domain.EntityState;
import com.gi.hrm.mongo.MongoUtilService;
import lombok.val;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public record Project(
        ProjectId id,
        ProjectName name,
        TaskIdList taskIds,
        CategoryList categories,
        ProjectOwner owner,
        EntityState aggregateState
) implements AggregateRoot<ProjectId, Project> {
    public static final String SEQUENCE_NAME = "projects_sequence";

    @Override
    public ProjectId getId() {
        return this.id;
    }

    @Override
    public EntityState getAggregateState() {
        return this.aggregateState;
    }

    @Override
    public Project persist() {
        return new Project(
                this.id,
                this.name,
                this.taskIds,
                this.categories,
                this.owner,
                this.aggregateState.persist()
        );
    }

    public Project deepCopyWithState(EntityState entityState) {
        return new Project(
                this.id,
                this.name,
                this.taskIds,
                this.categories,
                this.owner,
                entityState
        );
    }

    public static Mono<Project> create(
            ProjectName name,
            List<CategoryList.CategoryCreateParamRecord> categories,
            ProjectOwner owner,
            MongoUtilService mongoService
    ) {
        val idMono = ProjectId.create(mongoService);
        val categoryListMono = CategoryList.create(categories, mongoService);
        return Mono.zip(idMono, categoryListMono).map(tuple -> new Project(
                tuple.getT1(),
                name,
                new TaskIdList(),
                tuple.getT2(),
                owner,
                EntityState.create()
        ));
    }

    public Mono<Project> change(ProjectName projectName,
                                List<CategoryList.CategoryChangeParamRecord> categoriesChange,
                                ProjectOwner projectOwner,
                                MongoUtilService mongoService) {
        return this.categories.change(categoriesChange, mongoService).map(changedCategoryList ->
                new Project(
                        this.id,
                        projectName,
                        this.taskIds,
                        changedCategoryList,
                        projectOwner,
                        this.aggregateState.update()
                )
        );
    }

    public record ProjectDto(
            Integer id,
            String name,
            List<Category.CategoryDto> categories,
            String owner,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

    }

    public ProjectDto toDto() {
        return new ProjectDto(
                this.id.value(),
                this.name.value(),
                this.categories.stream().map(Category::toDto).toList(),
                this.owner.value(),
                this.aggregateState.createdAt(),
                this.aggregateState.updatedAt()
        );
    }

    public Project stateDelete() {
        return this.deepCopyWithState(this.getAggregateState().delete());
    }
}
