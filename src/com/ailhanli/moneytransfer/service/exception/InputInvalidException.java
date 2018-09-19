package com.ailhanli.moneytransfer.service.exception;

public class InputInvalidException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public InputInvalidException(String message) {
		super(message);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}