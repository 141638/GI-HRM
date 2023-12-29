package com.gi.hrm.common.entity;

public class CommonEntityProperties {
	private CommonEntityProperties() {
		throw new IllegalStateException("Utility class");
	}

	public static final String COLLECTION_DATABASE_SEQUENCE = "database_sequences";
	public static final String COLLECTION_WORKSPACE = "workspaces";
	public static final String COLLECTION_CATEGORY = "categories";
	public static final String COLLECTION_ISSUE_TYPE = "issue_types";
	public static final String COLLECTION_LOG_DETAIL = "log_details";
	public static final String COLLECTION_LOG_HISTORY = "log_histories";

	public static final String FIELD_ID = "_id";
	public static final String FIELD_CREATED_AT = "created_at";
	public static final String FIELD_CREATED_BY = "created_by";
	public static final String FIELD_UPDATED_AT = "updated_at";
	public static final String FIELD_UPDATED_BY = "updated_by";
	public static final String FIELD_DELETE_FLAG = "delete_flag";
}
