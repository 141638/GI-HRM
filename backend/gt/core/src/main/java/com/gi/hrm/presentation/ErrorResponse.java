package com.gi.hrm.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    /** error timestamp */
    private Date timestamp;
    /** API path */
    private String path;
    /** error message */
    private String message;
    /** HTTP status */
    private HttpStatus status;
    /** error details */
    private Object details;
    /** request ID */
    private String requestId;
}
