package com.gi.hrm.exception;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(Object item) {
		super(Objects.toString(item));
	}
}
