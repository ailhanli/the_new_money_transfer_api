package com.ailhanli.moneytransfer.service.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends Exception {

	public static final String ERROR_CODE=ErrorCodes.ERROR_CODE_INSUFFICIENT_BALANCE;
	
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE= "Sorry you don't have enough balance! You current balance is ";
	
	public InsufficientBalanceException(BigDecimal balance) {
		super(DEFAULT_MESSAGE+balance);
	}
}