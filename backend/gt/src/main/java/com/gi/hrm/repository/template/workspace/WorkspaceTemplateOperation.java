package com.gi.hrm.repository.template.workspace;

import com.gi.hrm.dto.response.workspace.WorkspaceSearchResponse;

import reactor.core.publisher.Flux;

public interface WorkspaceTemplateOperation {
	Flux<WorkspaceSearchResponse> search(String name, String projectId, String staffId);
}
