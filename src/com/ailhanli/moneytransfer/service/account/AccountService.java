package com.ailhanli.moneytransfer.service.account;

import java.util.List;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;

public interface AccountService {

	List<Account> getAllAccounts() throws GeneralSystemException;

	Account getAccount(Integer accountId)
			throws AccountNotFoundException, InputInvalidException, GeneralSystemException;
	
	void updateAccount(Account account) throws GeneralSystemException;
}