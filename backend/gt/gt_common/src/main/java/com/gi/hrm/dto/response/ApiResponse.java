package com.gi.hrm.dto.response;

import org.springframework.http.HttpStatus;

import com.gi.hrm.common.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	private Integer status;
	private String message;
	private Object item;

	public static Mono<ApiResponse> reactiveApiResponseSuccess(Mono<?> mono) {
		return mono.map(ApiResponse::apiResponseSuccess);
	}

	public static Flux<ApiResponse> reactiveApiResponseSuccess(Flux<?> flux) {
		return flux.map(ApiResponse::apiResponseSuccess);
	}

	public static Mono<ApiResponse> reactiveApiResponseErrorHandler(HttpStatus status, Mono<?> mono) {
		return mono.map(object -> ApiResponse.apiResponseErrorHandler(status, object));
	}

	public static ApiResponse apiResponseSuccess(Object object) {
		return new ApiResponse(Constants.HTTP_200, Constants.SUCCESS, object);
	}

	public static ApiResponse apiResponseErrorHandler(HttpStatus status, Object object) {
		return new ApiResponse(status.value(), status.toString(), object);
	}
}
