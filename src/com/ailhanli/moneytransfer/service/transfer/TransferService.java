package com.ailhanli.moneytransfer.service.transfer;

import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.InsufficientBalanceException;

/**
 * Transfer Service class for business logic
 */
public interface TransferService {

	void transferMoney(Transfer transfer)
			throws AccountNotFoundException, InsufficientBalanceException, InputInvalidException,GeneralSystemException;
}