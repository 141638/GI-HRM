package com.gi.hrm.task.query.query;

import java.time.LocalDateTime;

public record CategoryQueryModel(
        Integer id,
        String name,
        String color,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
