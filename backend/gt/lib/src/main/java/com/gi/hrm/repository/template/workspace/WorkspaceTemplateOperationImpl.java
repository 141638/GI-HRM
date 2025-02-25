package com.gi.hrm.repository.template.workspace;

import com.gi.hrm.repository.template.CommonTemplateOperation;
import org.springframework.stereotype.Repository;

@Repository
public class WorkspaceTemplateOperationImpl extends CommonTemplateOperation implements WorkspaceTemplateOperation {

    public WorkspaceTemplateOperationImpl() {
    }

//    @Override
//    public Flux<WorkspaceSearchResponse> search(String name, String projectId, String staffId) {
//
//        var nameMatch = createCriteriaWhereLike(WORKSPACE_NAME, name, false);
//        var projectIdMatch = createCriteriaWhereEqual(WORKSPACE_PROJECT_ID, projectId, Integer.class);
//        var staffIdMatch = createCriteriaWhereEqual(WORKSPACE_MEMBER_ID, staffId, Integer.class);
//        MatchOperation matchStage = matchTemplateBuild(nameMatch, projectIdMatch, staffIdMatch);
//
//        String[] projectionArray = {COMMON_ID, WORKSPACE_NAME, WORKSPACE_ALIAS};
//        Map<String, Object> projectionExtendMapping = new HashMap<>();
//        projectionExtendMapping.put("projectName", WORKSPACE_PROJECT_NAME);
//        projectionExtendMapping.put("projectCuratorName", WORKSPACE_PROJECT_CURATOR_NAME);
//        projectionExtendMapping.put("totalMember", ArrayOperators.Size.lengthOfArray(WORKSPACE_MEMBER));
//        ProjectionOperation projectStage = projectionTemplateBuild(projectionArray, projectionExtendMapping);
//
//        var aggPipeline = Aggregation.newAggregation(Workspace.class, matchStage, projectStage);
//        return mongoTemplate.aggregate(aggPipeline, WorkspaceSearchResponse.class);
//    }
}
