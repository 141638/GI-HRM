package com.gi.hrm.common;

public class CommonDocumentField {
	private CommonDocumentField() {
		throw new IllegalStateException("Constants class");
	}

	public static final String COMMON_ID = "id";

	public static final String WORKSPACE_NAME = "name";
	public static final String WORKSPACE_ALIAS = "alias";
	public static final String WORKSPACE_PROJECT_ID = "project.id";
	public static final String WORKSPACE_PROJECT = "project";
	public static final String WORKSPACE_PROJECT_NAME = "project.name";
	public static final String WORKSPACE_PROJECT_CURATOR_ID = "project.curator.id";
	public static final String WORKSPACE_PROJECT_CURATOR_NAME = "project.curator.name";
	public static final String WORKSPACE_MEMBER = "member";
	public static final String WORKSPACE_MEMBER_ID = "member.id";
	public static final String WORKSPACE_MEMBER_NAME = "member.name";
}
