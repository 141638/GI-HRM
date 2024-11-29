package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.ValueObject;
import com.gi.hrm.exception.BadRequestException;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "value")
public class TaskIdList implements ValueObject {
    private static final Integer MAX_TASK_COUNT = 500;

    private final List<TaskId> value;

    public TaskIdList() {
        this.value = new ArrayList<>();
    }

    public TaskIdList(List<TaskId> value) {
        if (value.size() > MAX_TASK_COUNT) {
            throw new BadRequestException("Task in project exceeds the max number");
        }
        this.value = value;
    }
}
