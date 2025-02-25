package com.gi.hrm.task.query.usecase;

import com.gi.hrm.query.query.Page;
import com.gi.hrm.query.query.ResultPageResponse;
import com.gi.hrm.task.query.query.ProjectQuery;
import com.gi.hrm.task.query.query.ProjectQueryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindProjectsUsecase {
    private final ProjectQuery projectQuery;

    public Mono<ResultPageResponse<ProjectQueryModel>> execute(FindProjectsParam param) {
        return projectQuery.findWithPagination(new Page(param.pageNo(), param.pageSize()));
    }
}
