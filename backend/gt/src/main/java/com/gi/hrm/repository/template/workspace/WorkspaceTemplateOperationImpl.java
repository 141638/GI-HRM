package com.gi.hrm.repository.template.workspace;

import static com.gi.hrm.common.CommonDocumentField.COMMON_ID;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_ALIAS;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_MEMBER;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_MEMBER_ID;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_NAME;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_PROJECT_ID;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_PROJECT_NAME;
import static com.gi.hrm.common.CommonDocumentField.WORKSPACE_PROJECT_CURATOR_NAME;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Repository;

import com.gi.hrm.dto.response.workspace.WorkspaceSearchResponse;
import com.gi.hrm.entity.Workspace;
import com.gi.hrm.repository.template.CommonTemplateOperation;

import reactor.core.publisher.Flux;

@Repository
public class WorkspaceTemplateOperationImpl extends CommonTemplateOperation implements WorkspaceTemplateOperation {

	public WorkspaceTemplateOperationImpl(ReactiveMongoTemplate mongoTemplate) {
		super(mongoTemplate);
	}

	@Override
	public Flux<WorkspaceSearchResponse> search(String name, String projectId, String staffId) {

		var nameMatch = createCriteriaWhereLike(WORKSPACE_NAME, name, false);
		var projectIdMatch = createCriteriaWhereEqual(WORKSPACE_PROJECT_ID, projectId, Integer.class);
		var staffIdMatch = createCriteriaWhereEqual(WORKSPACE_MEMBER_ID, staffId, Integer.class);
		MatchOperation matchStage = matchTemplateBuild(nameMatch, projectIdMatch, staffIdMatch);

		String[] projectionArray = { COMMON_ID, WORKSPACE_NAME, WORKSPACE_ALIAS };
		Map<String, Object> projectionExtendMapping = new HashMap<>();
		projectionExtendMapping.put("projectName", WORKSPACE_PROJECT_NAME);
		projectionExtendMapping.put("projectCuratorName", WORKSPACE_PROJECT_CURATOR_NAME);
		projectionExtendMapping.put("totalMember", ArrayOperators.Size.lengthOfArray(WORKSPACE_MEMBER));
		ProjectionOperation projectStage = projectionTemplateBuild(projectionArray, projectionExtendMapping);

		var aggPipeline = Aggregation.newAggregation(Workspace.class, matchStage, projectStage);
		return mongoTemplate.aggregate(aggPipeline, WorkspaceSearchResponse.class);
	}
}
