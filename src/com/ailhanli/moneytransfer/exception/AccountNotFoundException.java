package com.ailhanli.moneytransfer.exception;

public class AccountNotFoundException extends Exception {

	public static final String ERROR_CODE=ErrorCodes.ERROR_CODE_ACCOUNT_NOT_FOUND;
	
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE= "The account is not found. Account Id is ";
	
	public AccountNotFoundException(int accountId) {
		super(DEFAULT_MESSAGE+accountId);
	}
}