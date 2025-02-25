package com.gi.hrm.task.command.usecase;

import java.util.List;

public record ChangeProjectParam(
        Integer id,
        String name,
        List<Category> categories,
        String owner
) {
    public record Category(
            Integer id,
            String name,
            String color
    ) {

    }
}
