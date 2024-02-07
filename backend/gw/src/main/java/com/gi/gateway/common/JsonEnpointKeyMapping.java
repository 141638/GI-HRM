package com.gi.gateway.common;

public class JsonEnpointKeyMapping {
	private JsonEnpointKeyMapping() {
		throw new IllegalStateException("Utility class");
	}

	public static final String RESOURCE_ENDPOINTS_JSON = "json/resource-endpoints.json";
	public static final String EMPLOYEE = "resource-employee";
	public static final String TASKLOG = "resource-tasklog";
}
