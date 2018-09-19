package com.ailhanli.moneytransfer.exception;

public class GeneralSystemException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE="Oops system error occured. Please try again later";

	public GeneralSystemException() {
		super(DEFAULT_MESSAGE);
	}	
}