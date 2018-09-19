package com.ailhanli.moneytransfer.service.account;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailhanli.moneytransfer.dao.account.AccountDAO;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;

@Service
public class AccountServiceImpl implements AccountService {
	private static Logger log = Logger.getLogger(AccountServiceImpl.class);

	private final AccountDAO accountDAO;

	public AccountServiceImpl(AccountDAO accountDAO) {
		super();
		this.accountDAO = accountDAO;
	}

	/**
	 * getAccount methods return Account base on given accountId parameter
	 * 
	 * @throws GeneralSystemException
	 */
	@Override
	public Account getAccount(String accountId)
			throws AccountNotFoundException, InputInvalidException, GeneralSystemException {

		AccountIdValidator accountIdValidator = new AccountIdValidator();
		accountIdValidator.validateAccountNumber(accountId);

		Integer accountIdAsInteger = Integer.valueOf(accountId);
		try {
			return accountDAO.findById(accountIdAsInteger);
		} catch (RecordNotFoundException e) {
			log.warn(e);
			throw new AccountNotFoundException(accountIdAsInteger);
		} catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}
	}

	/**
	 * getAllAccounts method simply return all accounts from database
	 */
	@Override
	public List<Account> getAllAccounts() throws GeneralSystemException {
		try {
			 List<Account> accounts=  accountDAO.getAllAccounts();
			 return accounts;
		} catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}
	}

	/**
	 * updateBalance method simply updates balance by accountId
	 */
	@Override
	public Account updateBalance(Integer accountId, BigDecimal newBalance) throws GeneralSystemException {
		try {
			return accountDAO.update(accountId, newBalance);
		} catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}
	}
}
