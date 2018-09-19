package com.ailhanli.moneytransfer.service.transfer;

import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.util.ValidationUtil;

/**
 * Validator class for Transfer ID validation
 */
public class TransferIdValidator {

	public void validateAccountNumber(String transferId) throws InputInvalidException {
		if(!ValidationUtil.isAccountNumberValid(transferId)) {
			throw new InputInvalidException("TransferId is empty or it is not a number. It must be a valid number");
		}
	}
}