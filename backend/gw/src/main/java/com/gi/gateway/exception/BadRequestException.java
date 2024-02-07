package com.gi.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Integer statusCode;

	public BadRequestException(String msg, Integer statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}
}