package com.gi.hrm.task.infrastructure;


import com.gi.hrm.query.query.Page;
import com.gi.hrm.query.query.ResultPageResponse;
import com.gi.hrm.repository.template.CommonTemplateOperation;
import com.gi.hrm.task.query.query.ProjectQuery;
import com.gi.hrm.task.query.query.ProjectQueryModel;
import com.gi.hrm.task.query.query.ProjectWithCategoriesQueryModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProjectMongoQuery extends CommonTemplateOperation implements ProjectQuery {
    private final ProjectMapper projectMapper;
    private final CategoryMapper categoryMapper;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<ResultPageResponse<ProjectQueryModel>> findWithPagination(Page page) {
        val query = initQueryTemplate(mongoTemplate);
        return findWithPagination(query, ProjectRecord.class, page.page(), page.limit(), ProjectRecord::toQueryModel);
    }

    @Override
    public Mono<ProjectWithCategoriesQueryModel> findById(Integer projectId) {
        val projectRecordMono = projectMapper.findById(projectId);
        val categoryRecordsMono = categoryMapper.findByProjectId(projectId).collectList();
        return Mono.zip(projectRecordMono, categoryRecordsMono)
                .map(zip -> zip.getT1().toProjectWithCategoriesQueryModel(zip.getT2()));
    }
}
