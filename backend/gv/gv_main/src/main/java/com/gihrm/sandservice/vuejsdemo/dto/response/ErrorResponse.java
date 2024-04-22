package com.gihrm.sandservice.vuejsdemo.dto.response;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;

public class ErrorResponse {
	private HttpStatus status;
	private Date time;
	private String message;
	private Map<String, String> details;

	public ErrorResponse(HttpStatus status, Date time, String message, Map<String, String> details) {
		this.details = details;
		this.message = message;
		this.status = status;
		this.time = time;
	}

	public static Mono<ErrorResponse> reactiveApiResponseErrorHandler(HttpStatus status, Mono<?> mono) {
		return mono.map(object -> apiResponseErrorHandler(status, object));
	}

	public static ErrorResponse apiResponseErrorHandler(HttpStatus status, Object object) {
		return new ErrorResponse(status, new Date(), "error", Map.of("objectError", object.toString()));
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
