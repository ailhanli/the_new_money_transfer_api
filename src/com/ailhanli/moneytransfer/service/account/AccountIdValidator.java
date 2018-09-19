package com.ailhanli.moneytransfer.service.account;

import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.util.ValidationUtil;

/**
 * This class simply validate account number
 * */
public class AccountIdValidator {

	public void validateAccountNumber(String accountNumber) throws InputInvalidException {
		if(!ValidationUtil.isAccountNumberValid(accountNumber)) {
			throw new InputInvalidException("AccountId is empty or it is not a number. It must be a valid number");
		}
	}
}