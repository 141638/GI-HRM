package com.gi.hrm.task.command.usecase;

import com.gi.hrm.mongo.MongoUtilService;
import com.gi.hrm.task.command.domain.CategoryColor;
import com.gi.hrm.task.command.domain.CategoryList;
import com.gi.hrm.task.command.domain.CategoryName;
import com.gi.hrm.task.command.domain.Project;
import com.gi.hrm.task.command.domain.ProjectName;
import com.gi.hrm.task.command.domain.ProjectOwner;
import com.gi.hrm.task.command.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateProjectUsecase {
    private final ProjectRepository projectRepository;

    public Mono<Project.ProjectDto> execute(CreateProjectParam param, MongoUtilService mongoService) {

        Mono<Project> projectMono = Project.create(
                new ProjectName(param.name()),
                param.categories().stream()
                        .map(item -> new CategoryList.CategoryCreateParamRecord(
                                new CategoryName(item.name()),
                                new CategoryColor(item.color())
                        ))
                        .toList(),
                new ProjectOwner(param.owner()),
                mongoService
        ).cache();

        Mono<Project> savedProjectMono = projectMono.flatMap(projectRepository::save);

        Mono<Project.ProjectDto> toDtoMono = projectMono.map(Project::toDto);

        return savedProjectMono.then(toDtoMono);
    }
}
