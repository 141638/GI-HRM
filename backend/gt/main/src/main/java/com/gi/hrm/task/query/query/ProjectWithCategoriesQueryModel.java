package com.gi.hrm.task.query.query;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectWithCategoriesQueryModel(
        Integer id,
        String name,
        List<CategoryQueryModel> categories,
        String owner,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
