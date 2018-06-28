package com.ipiecoles.java.mdd050.exception;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleEntityNotFoundException(EntityNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleEntityExistsException(EntityExistsException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleEmployeException(EmployeException e) {
		return e.getMessage();
	}
}
