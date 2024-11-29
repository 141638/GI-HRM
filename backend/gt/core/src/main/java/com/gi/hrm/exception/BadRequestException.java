package com.gi.hrm.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    /** serial version UID */
    @Serial
    private static final long serialVersionUID = 1L;

    /** HTTP status code that can be customized */
    private final Integer statusCode;

    /**
     * Exception constructor set error message with statusCode=400
     *
     * @param msg error message
     */
    public BadRequestException(final String msg) {
        super(msg);
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }

    /**
     * Exception constructor set error message and status code
     *
     * @param msg error message
     * @param status customized status code
     */
    public BadRequestException(final String msg, final Integer status) {
        super(msg);
        this.statusCode = status;
    }
}
