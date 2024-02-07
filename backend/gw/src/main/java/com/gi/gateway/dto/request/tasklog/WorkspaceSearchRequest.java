package com.gi.gateway.dto.request.tasklog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceSearchRequest {
	private String name;
	private Integer projectId;
	private Integer staffId;
}
