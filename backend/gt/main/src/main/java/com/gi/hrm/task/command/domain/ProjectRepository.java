package com.gi.hrm.task.command.domain;

import com.gi.hrm.command.domain.Repository;

import java.util.List;

public interface ProjectRepository extends Repository<ProjectId, Project> {
    List<Project> findAll();
}
