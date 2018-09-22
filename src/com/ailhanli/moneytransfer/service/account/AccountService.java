package com.ailhanli.moneytransfer.service.account;

import java.util.List;

import com.ailhanli.moneytransfer.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.model.Account;

public interface AccountService {

	List<Account> getAllAccounts() throws GeneralSystemException;

	Account getAccount(Integer accountId)
			throws AccountNotFoundException, InputInvalidException, GeneralSystemException;
	
	boolean updateAccount(Account account) throws GeneralSystemException;
	
	boolean deleteAccount(Integer accountId) throws AccountNotFoundException;
	
	Integer createNewAccount(Account account)throws GeneralSystemException;
}