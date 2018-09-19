package com.ailhanli.moneytransfer.service.exception;

public class InsufficientBalanceException extends Exception {

	public static final String ERROR_CODE=ErrorCodes.ERROR_CODE_INSUFFICIENT_BALANCE;
	
	private static final long serialVersionUID = 1L;
	
	public InsufficientBalanceException(String message) {
		super(message);
	}
}