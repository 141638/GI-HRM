package com.gi.gateway.dto.response.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PreBuiltReactiveServerResponse {
	private PreBuiltReactiveServerResponse() {
		throw new IllegalStateException("Utility class");
	}

	public static Mono<ServerResponse> success(ApiResponse apiResponse) {
		return ServerResponse.ok().body(Mono.just(apiResponse), ApiResponse.class);
	}

	public static Mono<ServerResponse> success(Mono<?> responseObject) {
		return ServerResponse.ok().body(ApiResponse.reactiveApiResponseSuccess(responseObject), ApiResponse.class);
	}

	public static <T> Mono<ServerResponse> success(Flux<T> responseList) {
		return ServerResponse.ok().body(responseList, responseList.getClass());
	}

	public static <T> Mono<ServerResponse> successSSE(Flux<T> responseList) {
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(responseList, responseList.getClass());
	}

	public static Mono<ServerResponse> badRequest(Object responseObject) {
		return ServerResponse.badRequest().body(Mono.just(responseObject), ApiResponse.class);
	}

	public static Mono<ServerResponse> badRequest(Mono<?> responseObject) {
		return ServerResponse.badRequest().body(
		        ErrorResponse.reactiveApiResponseErrorHandler(HttpStatus.BAD_REQUEST, responseObject),
		        ApiResponse.class);
	}
}
