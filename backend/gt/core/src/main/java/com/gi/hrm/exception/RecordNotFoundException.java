package com.gi.hrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Objects;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    /** serial version UID */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default exception constructor, without error message
     */
    public RecordNotFoundException() {
        super();
    }

    /**
     * Constructor set object item as error message
     *
     * @param item object item
     */
    public RecordNotFoundException(final Object item) {
        super(Objects.toString(item));
    }
}
