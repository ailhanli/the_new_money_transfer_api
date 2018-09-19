package com.ailhanli.moneytransfer.model;

import com.ailhanli.moneytransfer.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.exception.ErrorCodes;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.exception.TransferNotFoundException;

public class Error {
	private static final String UNKNOWN_ERROR_MESSAGE = "Sorry system error occured. Please try again later";

	private String errorId;

	private String errorDesription;

	private Error(String errorId, String errorDesription) {
		super();
		this.errorId = errorId;
		this.errorDesription = errorDesription;
	}

	public static Error of(String errorId, String errorDesription) {
		return new Error(errorId, errorDesription);
	}

	public static Error of(Exception e) {
		if (e instanceof InsufficientBalanceException) {
			return Error.of(ErrorCodes.ERROR_CODE_INSUFFICIENT_BALANCE, e.getMessage());
		} else if (e instanceof AccountNotFoundException) {
			return Error.of(ErrorCodes.ERROR_CODE_ACCOUNT_NOT_FOUND, e.getMessage());
		} else if (e instanceof TransferNotFoundException) {
			return Error.of(ErrorCodes.ERROR_CODE_TRANSFER_NOT_FOUND, e.getMessage());
		} else if (e instanceof InputInvalidException) {
			return Error.of(ErrorCodes.ERROR_CODE_INVALID_INPUT, e.getMessage());
		} else if (e instanceof GeneralSystemException) {
			return Error.of(ErrorCodes.ERROR_CODE_SYSTEM_ERROR, e.getMessage());
		} else {
			return Error.of(ErrorCodes.ERROR_CODE_UNKNOWN_ERROR, UNKNOWN_ERROR_MESSAGE);
		}
	}

	public String getErrorDesription() {
		return errorDesription;
	}

	public String getErrorId() {
		return errorId;
	}

	@Override
	public String toString() {
		return "Error [errorId=" + errorId + ", errorDesription=" + errorDesription + "]";
	}
}