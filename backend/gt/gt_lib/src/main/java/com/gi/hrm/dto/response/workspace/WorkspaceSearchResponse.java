package com.gi.hrm.dto.response.workspace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceSearchResponse {
	private Integer id;
	private String name;
	private String alias;
	private String projectName;
	private String projectCuratorName;
	private Integer totalMember;
}
