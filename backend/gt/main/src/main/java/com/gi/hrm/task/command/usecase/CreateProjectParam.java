package com.gi.hrm.task.command.usecase;

import java.util.List;

public record CreateProjectParam(
        String name,
        List<Category> categories,
        String owner
) {
    public record Category(
            String name,
            String color
    ) {

    }
}
