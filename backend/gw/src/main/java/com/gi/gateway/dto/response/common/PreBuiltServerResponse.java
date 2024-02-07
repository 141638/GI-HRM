package com.gi.gateway.dto.response.common;

import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class PreBuiltServerResponse {
	private PreBuiltServerResponse() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> Mono<ServerResponse> success(T apiResponse) {
		return ServerResponse.ok().body(apiResponse, ApiResponse.class);
	}

	public static Mono<ServerResponse> badRequest(Object responseObject) {
		return ServerResponse.badRequest().body(responseObject, ApiResponse.class);
	}
}
