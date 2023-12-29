package com.gi.hrm.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PreBuiltServerResponse {
	private PreBuiltServerResponse() {
		throw new IllegalStateException("Utility class");
	}

	public static Mono<ServerResponse> success(Mono<?> responseObject) {
		return ServerResponse.ok().body(ApiResponse.reactiveApiResponseSuccess(responseObject), ApiResponse.class);
	}

	public static <T> Mono<ServerResponse> success(Flux<T> responseList) {
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
		        .body(ApiResponse.reactiveApiResponseSuccess(responseList), ApiResponse.class);
	}

	public static Mono<ServerResponse> badRequest(Mono<?> responseObject) {
		return ServerResponse.badRequest().body(
		        ErrorResponse.reactiveApiResponseErrorHandler(HttpStatus.BAD_REQUEST, responseObject),
		        ApiResponse.class);
	}
}
