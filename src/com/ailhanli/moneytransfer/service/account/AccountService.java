package com.ailhanli.moneytransfer.service.account;

import java.math.BigDecimal;
import java.util.List;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;

public interface AccountService {

	List<Account> getAllAccounts() throws GeneralSystemException;

	Account getAccount(String accountId) throws AccountNotFoundException, InputInvalidException, GeneralSystemException;

	Account updateBalance(Integer accountId, BigDecimal newBalance) throws GeneralSystemException;
}