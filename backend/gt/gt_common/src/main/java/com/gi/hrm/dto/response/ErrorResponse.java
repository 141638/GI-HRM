package com.gi.hrm.dto.response;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	private HttpStatus status;
	private Date time;
	private String message;
	private Map<String, String> details;

	public static Mono<ErrorResponse> reactiveApiResponseErrorHandler(HttpStatus status, Mono<?> mono) {
		return mono.map(object -> apiResponseErrorHandler(status, object));
	}

	public static ErrorResponse apiResponseErrorHandler(HttpStatus status, Object object) {
		return new ErrorResponse(status, new Date(), "error", Map.of("objectError", object.toString()));
	}
}
