package com.gi.hrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gi.hrm.common.Constants;

@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
public class ObjectFieldsMismatchException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ObjectFieldsMismatchException() {
        super(Constants.OBJECT_FIELD_MISMATCH);
    }
}
