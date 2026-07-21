package com.hex.maverickbank.exception;

public class InvalidUsernameException extends Exception{

	private static final long serialVersionUID = 1L;

	private final String message;
	
	
	public InvalidUsernameException(String message) {
		super();
		this.message = message;
	}


	@Override
	public String getMessage() {
		return message;
	}
}
