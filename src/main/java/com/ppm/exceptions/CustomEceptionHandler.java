package com.ppm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomEceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectIdException(ProjectIdExceptionHandler ex, WebRequest request) {
		ProjectIdExceptionResponse response = new ProjectIdExceptionResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	};

	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex,
			WebRequest request) {
		ProjectNotFoundExceptionResponse response = new ProjectNotFoundExceptionResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	};
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleUserAlreadyExistException(UserNameAlreadyExistException ex,
			WebRequest request) {
		UsernameAlreadyExistResponse response = new UsernameAlreadyExistResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	};

}
