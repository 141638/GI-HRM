package com.gi.hrm.task.command.usecase;

import com.gi.hrm.task.command.domain.Project;
import com.gi.hrm.task.command.domain.ProjectId;
import com.gi.hrm.task.command.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteProjectUsecase {
    private final ProjectRepository projectRepository;

    public Mono<Void> execute(DeleteProjectParam param) {
        return projectRepository.findById(new ProjectId(param.projectId()))
                .map(Project::stateDelete)
                .flatMap(projectRepository::save)
                .then();
    }
}
