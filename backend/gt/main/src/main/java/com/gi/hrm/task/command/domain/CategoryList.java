package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.ValueObject;
import com.gi.hrm.exception.BadRequestException;
import com.gi.hrm.mongo.MongoUtilService;
import com.gi.hrm.utils.ValidationUtils;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.val;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode(of = "value")
public class CategoryList implements ValueObject {
    public static final int MIN_CATEGORY_COUNT = 1;
    public static final int MAX_CATEGORY_COUNT = 50;

    private final List<Category> value;

    public CategoryList(List<Category> value) {
        if (ValidationUtils.isNullOrEmpty(value)) {
            throw new IllegalStateException("Category list is required");
        } else {
            val size = value.size();
            if (size < MIN_CATEGORY_COUNT) {
                throw new BadRequestException("Category list is required");
            }
            if (size > MAX_CATEGORY_COUNT) {
                throw new BadRequestException("Category quantity cannot exceed 50");
            }
        }

        this.value = value;
    }

    public record CategoryCreateParamRecord(
            CategoryName name,
            CategoryColor color
    ) { }

    public static Mono<CategoryList> create(List<CategoryCreateParamRecord> value, MongoUtilService mongoService) {
        return Flux.fromIterable(value).flatMap(createParam -> Category.create(
                createParam.name,
                createParam.color,
                mongoService
        )).collectList().map(CategoryList::new);
    }

    public record CategoryChangeParamRecord(
            @Nullable
            CategoryId id,
            CategoryName name,
            CategoryColor color
    ) { }

    public Mono<CategoryList> change(List<CategoryChangeParamRecord> values, MongoUtilService mongoService) {
        return Flux.fromIterable(values).flatMap(categoryToChange -> {
            if (Objects.isNull(categoryToChange.id())) {
                return Category.create(categoryToChange.name(), categoryToChange.color(), mongoService);
            } else {
                return Mono.just(get(categoryToChange.id()).change(categoryToChange.name(), categoryToChange.color()));
            }
        }).collectList().map(CategoryList::new);
    }

    public Stream<Category> stream() {
        return Objects.requireNonNull(this.value.stream());
    }

    public Category get(int idx) {
        return Objects.requireNonNull(this.value.get(idx), "index is out of bound");
    }


    public Category get(CategoryId id) {
        return this.value.stream().filter(item -> item.id().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalStateException("Category not found for id=" + id.id()));
    }
}
