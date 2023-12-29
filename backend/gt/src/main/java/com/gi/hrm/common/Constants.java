package com.gi.hrm.common;

public class Constants {
	// HTTP status
	public static final Integer HTTP_200 = 200;
	public static final Integer HTTP_400 = 400;
	public static final Integer HTTP_401 = 401;
	public static final Integer HTTP_404 = 404;
	public static final Integer HTTP_500 = 500;

	// Status response
	public static final String SUCCESS = "Action success";
	public static final String FAILED = "Action failed";
	public static final String RECORD_NOT_FOUND = "Record not found - %s";
	public static final String CREDENTIAL_FALSE = "Username/email or password not found";
	public static final String OBJECT_FIELD_MISMATCH = "Entity field's and request param's size mismatch";

	public static final String VALIDATE_NOT_NULL = "Input field cannot be empty";
	public static final String DEFAULT_ERROR_MESSAGE = "Something is wrong here and I don't care to give a fucking detail message";

}
