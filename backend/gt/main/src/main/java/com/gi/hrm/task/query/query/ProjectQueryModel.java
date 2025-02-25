package com.gi.hrm.task.query.query;

import java.time.LocalDateTime;

public record ProjectQueryModel(
        Integer id,
        String name,
        String owner,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
