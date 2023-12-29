package com.gi.gateway.dto.response.common;

import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class PreBuiltServerResponse {

	public static Mono<ServerResponse> OK(Object responseObject) {
		return ServerResponse.ok().body(responseObject, ApiResponse.class);
	}

	public static Mono<ServerResponse> BAD_REQUEST(Object responseObject) {
		return ServerResponse.badRequest().body(responseObject, ApiResponse.class);
	}
}
