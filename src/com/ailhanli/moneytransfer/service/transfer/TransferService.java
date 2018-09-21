package com.ailhanli.moneytransfer.service.transfer;

import com.ailhanli.moneytransfer.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.model.Transfer;

/**
 * Transfer Service class for business logic
 */
public interface TransferService {

	void transferMoney(Transfer transfer)
			throws AccountNotFoundException, InsufficientBalanceException, InputInvalidException,GeneralSystemException;
}