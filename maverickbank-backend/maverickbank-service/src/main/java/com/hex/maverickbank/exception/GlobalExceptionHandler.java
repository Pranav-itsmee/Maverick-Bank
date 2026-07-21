package com.hex.maverickbank.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hex.maverickbank.dto.MessageResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MessageResponseDto> handleNotFound(ResourceNotFoundException e) {
		return build(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(InvalidUsernameException.class)
	public ResponseEntity<MessageResponseDto> handleInvalidUsername(InvalidUsernameException e) {
		return build(HttpStatus.CONFLICT, e.getMessage());
	}

	@ExceptionHandler(InvalidTransactionException.class)
	public ResponseEntity<MessageResponseDto> handleInvalidTransaction(InvalidTransactionException e) {
		return build(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MessageResponseDto> handleIllegalArgument(IllegalArgumentException e) {
		return build(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<MessageResponseDto> handleAuthentication(AuthenticationException e) {
		return build(HttpStatus.UNAUTHORIZED, "Invalid username or password");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageResponseDto> handleGeneric(Exception e) {
		logger.error("Unhandled exception", e);
		return build(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
	}

	private ResponseEntity<MessageResponseDto> build(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(new MessageResponseDto(message, status.value()));
	}
}
