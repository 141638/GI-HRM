package com.gi.hrm.task.infrastructure;

import com.gi.hrm.command.domain.EntityState;
import com.gi.hrm.query.query.CommonEntityProperties;
import com.gi.hrm.task.command.domain.CategoryList;
import com.gi.hrm.task.command.domain.Project;
import com.gi.hrm.task.command.domain.ProjectId;
import com.gi.hrm.task.command.domain.ProjectName;
import com.gi.hrm.task.command.domain.ProjectOwner;
import com.gi.hrm.task.command.domain.TaskIdList;
import com.gi.hrm.task.query.query.ProjectQueryModel;
import com.gi.hrm.task.query.query.ProjectWithCategoriesQueryModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value = CommonEntityProperties.COLLECTION_PROJECT)
public record ProjectRecord(
        Integer id,
        String name,
        String owner,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Project toDomain(List<CategoryRecord> categories) {
        return new Project(
                new ProjectId(this.id),
                new ProjectName(this.name),
                new TaskIdList(),
                new CategoryList(categories.stream().map(CategoryRecord::toDomain).toList()),
                new ProjectOwner(this.owner),
                new EntityState.Persisted(this.createdAt, this.updatedAt)
        );
    }

    public static ProjectRecord fromDomain(Project project) {
        return new ProjectRecord(
                project.id().value(),
                project.name().value(),
                project.owner().value(),
                project.aggregateState().createdAt(),
                project.aggregateState().updatedAt()
        );
    }

    public ProjectQueryModel toQueryModel() {
        return new ProjectQueryModel(
                this.id,
                this.name,
                this.owner,
                this.createdAt,
                this.updatedAt
        );
    }

    public ProjectWithCategoriesQueryModel toProjectWithCategoriesQueryModel(List<CategoryRecord> categories) {
        return new ProjectWithCategoriesQueryModel(
                this.id,
                this.name,
                categories.stream().map(CategoryRecord::toCategoryQueryModel).toList(),
                this.owner,
                this.createdAt,
                this.updatedAt
        );
    }
}
