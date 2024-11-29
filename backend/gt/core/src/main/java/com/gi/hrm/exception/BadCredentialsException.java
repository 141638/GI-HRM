package com.gi.hrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends RuntimeException {
    /** serial version UID */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor set error message
     *
     * @param mess error message
     */
    public BadCredentialsException(final String mess) {
        super(mess);
    }

    /**
     * Default exception constructor, without error message
     */
    public BadCredentialsException() {
        super();
    }
}
