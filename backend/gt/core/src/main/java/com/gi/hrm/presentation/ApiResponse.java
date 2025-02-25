package com.gi.hrm.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    /** HTTP status */
    private Integer status;
    /** response message */
    private String message;
    /** response item */
    private Object item;

    /**
     * Map mono object into ApiResponse
     *
     * @param mono a mono object of item
     * @return ApiResponse
     */
    public static Mono<ApiResponse> reactiveApiResponseSuccess(final Mono<?> mono) {
        return mono.map(ApiResponse::apiResponseSuccess);
    }

    /**
     * Stream flux object and map into ApiResponse
     *
     * @param flux a flux stream of item
     * @return a flux stream of ApiResponse
     */
    public static Flux<ApiResponse> reactiveApiResponseSuccess(final Flux<?> flux) {
        return flux.map(ApiResponse::apiResponseSuccess);
    }

    /**
     * Create a success ApiResponse object with item
     *
     * @param item ApiResponse's item
     * @return ApiResponse
     */
    public static ApiResponse apiResponseSuccess(final Object item) {
        return new ApiResponse(HttpStatus.OK.value(), "SUCCESS", item);
    }

    /**
     * Create a success ApiResponse object with null item
     *
     * @return ApiResponse
     */
    public static ApiResponse apiResponseSuccess() {
        return new ApiResponse(HttpStatus.OK.value(), "SUCCESS", null);
    }
}
