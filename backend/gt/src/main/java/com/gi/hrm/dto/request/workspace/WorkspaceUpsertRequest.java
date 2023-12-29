package com.gi.hrm.dto.request.workspace;

import java.util.List;

import com.gi.hrm.entity.Project;
import com.gi.hrm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceUpsertRequest {
	private Integer id;
	private String name;
	private String alias;
	private Project project;
	private List<User> member;

}
