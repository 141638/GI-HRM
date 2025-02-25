package com.gi.hrm.presentation;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public final class PreBuiltServerResponse {
    /**
     * Utility class's private constructor
     */
    private PreBuiltServerResponse() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Map a mono object into an internal server error ServerResponse of ErrorResponse's item
     *
     * @param message error message
     * @return a 500 server response
     */
    public static Mono<ServerResponse> error(final String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .message(message)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse, ErrorResponse.class);
    }

    /**
     * Map a mono object into a success ServerResponse of ApiResponse's item
     *
     * @param responseObject mono object acts as ApiResponse's item
     * @return a 200 server response
     */
    public static Mono<ServerResponse> success(final Mono<?> responseObject) {
        return ServerResponse.ok().body(ApiResponse.reactiveApiResponseSuccess(responseObject), ApiResponse.class);
    }

    /**
     * Map a mono object of ApiResponse into a success ServerResponse
     *
     * @param apiResponseMono mono-wrapped ApiResponse object
     * @return a 200 server response
     */
    public static Mono<ServerResponse> apiResponseSuccess(final Mono<ApiResponse> apiResponseMono) {
        return ServerResponse.ok().body(apiResponseMono, ApiResponse.class);
    }

    /**
     * Return a success ServerResponse of a flux stream of object mapped into ApiResponse
     *
     * @param responseList flux stream to map into ApiResponse's item
     * @param <T> generic type of the object inside the flux stream
     * @return a 200 server response
     */
    public static <T> Mono<ServerResponse> success(final Flux<T> responseList) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(ApiResponse.reactiveApiResponseSuccess(responseList), ApiResponse.class);
    }

    /**
     * Return a success server response with its body is the raw flux stream of object(not mapped into ApiResponse)
     *
     * @param responseList flux stream to return
     * @param <T> generic type of the object inside the flux stream
     * @param type type of the object inside the flux stream
     * @return a 200 server response
     */
    public static <T> Mono<ServerResponse> rawFluxStreamSuccess(final Flux<T> responseList, final Class<T> type) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(responseList, type);
    }

    /**
     * Return a success server response with its body is a normal object
     *
     * @param obj normal object to return
     * @return a 200 server response
     */
    public static Mono<ServerResponse> success(final Object obj) {
        if (obj.getClass().isAssignableFrom(Publisher.class)) {
            return Mono.error(new IllegalStateException("this function does not receive `publisher` type object"));
        }
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(ApiResponse.apiResponseSuccess(obj)), ApiResponse.class);
    }

    /**
     * Return a success server response with its body is a flux stream of data buffer
     *
     * @param fileDataBuffer file data buffer
     * @return server response
     */
    public static Mono<ServerResponse> dataBuffer(final Flux<DataBuffer> fileDataBuffer) {
        return ServerResponse.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileDataBuffer, DataBuffer.class);
    }
}
