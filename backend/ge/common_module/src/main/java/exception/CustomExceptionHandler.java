package exception;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import common.Constants;
import dto.response.ErrorResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private Map<String, String> errors;

	@ExceptionHandler(ObjectFieldsMismatchException.class)
	public final ResponseEntity<Object> handleObjectFieldsMismatchException(ObjectFieldsMismatchException ex) {
		String errorMessage = String.format(Constants.OBJECT_FIELD_MISMATCH, ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, new Date(), errorMessage,
				errors);

		return new ResponseEntity<Object>(error, HttpStatus.OK);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(ObjectFieldsMismatchException ex) {
		String errorMessage = String.format(Constants.RECORD_NOT_FOUND, ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, new Date(), errorMessage, errors);

		return new ResponseEntity<Object>(error, HttpStatus.OK);
	}

}
