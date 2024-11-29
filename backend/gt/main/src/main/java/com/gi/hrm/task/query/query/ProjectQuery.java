package com.gi.hrm.task.query.query;

import com.gi.hrm.query.query.Page;
import com.gi.hrm.query.query.ResultPageResponse;
import reactor.core.publisher.Mono;

public interface ProjectQuery {
    Mono<ResultPageResponse<ProjectQueryModel>> findWithPagination(Page page);

    Mono<ProjectWithCategoriesQueryModel> findById(Integer projectId);
}
