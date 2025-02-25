package com.gi.hrm.task.infrastructure;

import com.gi.hrm.query.query.CommonEntityProperties;
import com.gi.hrm.task.command.domain.Category;
import com.gi.hrm.task.command.domain.CategoryColor;
import com.gi.hrm.task.command.domain.CategoryId;
import com.gi.hrm.task.command.domain.CategoryName;
import com.gi.hrm.task.command.domain.ProjectId;
import com.gi.hrm.task.query.query.CategoryQueryModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = CommonEntityProperties.COLLECTION_CATEGORY)
public record CategoryRecord(
        Integer id,
        Integer projectId,
        String name,
        String color,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public Category toDomain() {
        return new Category(
                new CategoryId(this.id),
                new CategoryName(this.name),
                new CategoryColor(this.color),
                this.createdAt,
                this.updatedAt
        );
    }

    public static CategoryRecord fromDomain(Category category, ProjectId projectId) {
        return new CategoryRecord(
                category.id().id(),
                projectId.value(),
                category.name().value(),
                category.color().value(),
                category.createdAt(),
                category.updatedAt()
        );
    }

    public CategoryQueryModel toCategoryQueryModel() {
        return new CategoryQueryModel(
                this.id,
                this.name,
                this.color,
                this.createdAt,
                this.updatedAt
        );
    }
}
