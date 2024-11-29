package com.gi.hrm.task.query.usecase;

import com.gi.hrm.task.query.query.ProjectQuery;
import com.gi.hrm.task.query.query.ProjectWithCategoriesQueryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindProjectUsecase {
    private final ProjectQuery projectQuery;

    public Mono<ProjectWithCategoriesQueryModel> execute(FindProjectParam param) {
        return projectQuery.findById(param.projectId());
    }
}
