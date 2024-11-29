package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.ValueObject;
import com.gi.hrm.mongo.MongoUtilService;
import lombok.val;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public record Category(
        CategoryId id,
        CategoryName name,
        CategoryColor color,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements ValueObject {
    public static final String SEQUENCE_NAME = "categories_sequence";

    /**
     * Create category
     *
     * @param name category name
     * @param color category color
     * @param mongoService mongo util service
     * @return mono-wrapped new registered category
     */
    public static Mono<Category> create(CategoryName name, CategoryColor color, MongoUtilService mongoService) {
        val now = LocalDateTime.now();
        val idMono = CategoryId.create(mongoService);
        return idMono.map(id -> new Category(id, name, color, now, now));
    }

    public Category change(CategoryName name, CategoryColor color) {
        return new Category(this.id, name, color, this.createdAt, LocalDateTime.now());
    }

    public record CategoryDto(
            Integer id,
            String name,
            String color,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) { }

    public CategoryDto toDto() {
        return new CategoryDto(
                this.id().id(),
                this.name().value(),
                this.color().value(),
                this.createdAt,
                this.updatedAt);
    }
}
