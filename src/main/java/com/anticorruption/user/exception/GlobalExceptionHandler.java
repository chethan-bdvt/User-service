package com.anticorruption.user.exception;

import com.anticorruption.user.controller.UserController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final UserController userController;

	GlobalExceptionHandler(UserController userController) {
		this.userController = userController;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<ErrorResponse> duplicateUserException(DuplicateUserException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> validationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->
		errors.put(error.getField(), error.getDefaultMessage()));
		
		return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.toString(), LocalDateTime.now()));	
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> genericException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An Unexpected error occured",
						LocalDateTime.now()));
	}
	
	
	
}
