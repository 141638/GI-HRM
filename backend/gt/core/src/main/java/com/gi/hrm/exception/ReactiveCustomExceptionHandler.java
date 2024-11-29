package com.gi.hrm.exception;

import com.gi.hrm.presentation.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@ControllerAdvice
public class ReactiveCustomExceptionHandler extends WebFluxResponseStatusExceptionHandler {
    /**
     * Custom handler for RecordNotFoundException
     *
     * @param ex exception
     * @param exchange HTTP request-response interaction
     * @return a mono object of 404 status ResponseEntity with an error response as its body
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public final Mono<ResponseEntity<Object>> handleRecordNotFoundException(final RecordNotFoundException ex,
                                                                            final ServerWebExchange exchange) {
        // error message
        String errorMessage = ex.getMessage();
        // path
        String currentPath = exchange.getRequest().getPath().toString();
        // request id
        String requestId = exchange.getRequest().getId();

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .path(currentPath)
                .status(HttpStatus.NOT_FOUND)
                .message(errorMessage)
                .requestId(requestId)
                .build();
        return Mono.just(new ResponseEntity<>(error, HttpStatus.NOT_FOUND));
    }

    /**
     * Custom handler for BadRequestException
     *
     * @param ex exception
     * @param exchange HTTP request-response interaction
     * @return a mono object of 400 status ResponseEntity with an error response as its body
     */
    @ExceptionHandler(BadRequestException.class)
    public final Mono<ResponseEntity<Object>> handleBadRequestException(final BadRequestException ex,
                                                                        final ServerWebExchange exchange) {
        // error message
        String errorMessage = ex.getMessage();
        // path
        String currentPath = exchange.getRequest().getPath().toString();
        // request id
        String requestId = exchange.getRequest().getId();
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .path(currentPath)
                .status(HttpStatus.BAD_REQUEST)
                .message(errorMessage)
                .requestId(requestId)
                .build();

        return Mono.just(new ResponseEntity<>(error, HttpStatus.BAD_REQUEST));
    }

    /**
     * Custom handler for ConflictException
     *
     * @param ex exception
     * @param exchange HTTP request-response interaction
     * @return a mono object of 409 status ResponseEntity with an error response as its body
     */
    @ExceptionHandler(ConflictException.class)
    public final Mono<ResponseEntity<Object>> handleConflictException(final ConflictException ex,
                                                                      final ServerWebExchange exchange) {
        // error message
        String errorMessage = ex.getMessage();
        // path
        String currentPath = exchange.getRequest().getPath().toString();
        // request id
        String requestId = exchange.getRequest().getId();
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(new Date())
                .path(currentPath)
                .status(HttpStatus.CONFLICT)
                .message(errorMessage)
                .requestId(requestId)
                .build();

        return Mono.just(new ResponseEntity<>(error, HttpStatus.CONFLICT));
    }
}
