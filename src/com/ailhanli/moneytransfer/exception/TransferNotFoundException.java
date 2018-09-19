package com.ailhanli.moneytransfer.exception;

public class TransferNotFoundException extends Exception {
	public static final String ERROR_CODE=ErrorCodes.ERROR_CODE_TRANSFER_NOT_FOUND;

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE= "The Transfer is not found. Transfer ID is ";
	
	public TransferNotFoundException(int id) {
		super(DEFAULT_MESSAGE+id);
	}
}