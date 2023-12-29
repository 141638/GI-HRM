package com.gi.hrm.security.jwt;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {
//	@Autowired
//	private Utils utils;

	@Value("classpath:json/open-api-endpoints.json")
	Resource jsonResource;

	@SuppressWarnings("unchecked")
	public List<String> openApiEndpoints() {
//		Map<String, Object> endpointParserMapping = utils.parseJsonResourceToMap(jsonResource);
//		Object endpoints = endpointParserMapping.get("endpoints");
//		return new ArrayList<>((Collection<String>) endpoints);
		return null;
	}

	public Boolean isSecured(ServerHttpRequest request) {
		final List<String> finalListEndpoint = openApiEndpoints();
		return finalListEndpoint.stream().noneMatch(uri -> request.getURI().getPath().equals(uri));
	}
}
