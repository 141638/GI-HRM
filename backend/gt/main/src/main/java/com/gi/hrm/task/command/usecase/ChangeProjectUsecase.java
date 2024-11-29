package com.gi.hrm.task.command.usecase;

import com.gi.hrm.mongo.MongoUtilService;
import com.gi.hrm.task.command.domain.CategoryColor;
import com.gi.hrm.task.command.domain.CategoryId;
import com.gi.hrm.task.command.domain.CategoryList;
import com.gi.hrm.task.command.domain.CategoryName;
import com.gi.hrm.task.command.domain.Project;
import com.gi.hrm.task.command.domain.ProjectId;
import com.gi.hrm.task.command.domain.ProjectName;
import com.gi.hrm.task.command.domain.ProjectOwner;
import com.gi.hrm.task.command.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeProjectUsecase {
    private final ProjectRepository projectRepository;

    public Mono<Project.ProjectDto> execute(ChangeProjectParam param, MongoUtilService mongoService) {
        return projectRepository.findById(new ProjectId(param.id()))
                .flatMap(project -> applyChanged(project, param, mongoService))
                .flatMap(projectRepository::save)
                .map(Project::toDto);
    }

    /**
     * Apply change param to the old state record
     *
     * @param oldStateRecord fetched from DB old state record
     * @param changeParam param to update to the record
     * @param mongoService mongo service bean
     * @return changed record
     */
    private Mono<Project> applyChanged(Project oldStateRecord,
                                       ChangeProjectParam changeParam,
                                       MongoUtilService mongoService) {
        return oldStateRecord.change(
                new ProjectName(changeParam.name()),
                changeParam.categories().stream().map(category -> new CategoryList.CategoryChangeParamRecord(
                        Optional.ofNullable(category.id()).map(CategoryId::new).orElse(null),
                        new CategoryName(category.name()),
                        new CategoryColor(category.color())
                )).toList(),
                new ProjectOwner(changeParam.owner()),
                mongoService
        );
    }
}
