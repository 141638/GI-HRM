package com.gihrm.sandservice.vuejsdemo.dto.response;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ApiResponse {
    private Integer status;
    private String message;
    private Object item;

    public ApiResponse(Integer status, String message, Object item) {
        this.item = item;
        this.message = message;
        this.status = status;
    }

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
        return new ApiResponse(HttpStatus.OK.value(), "Action success", object);
    }

    public static ApiResponse apiResponseErrorHandler(HttpStatus status, Object object) {
        return new ApiResponse(status.value(), status.toString(), object);
    }

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
