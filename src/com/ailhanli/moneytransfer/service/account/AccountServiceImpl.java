package com.ailhanli.moneytransfer.service.account;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailhanli.moneytransfer.dao.account.AccountDAO;
import com.ailhanli.moneytransfer.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

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
	public Account getAccount(Integer accountId) throws AccountNotFoundException, GeneralSystemException {

		try {
			return accountDAO.findById(accountId);
		} catch (RecordNotFoundException e) {
			log.warn(e);
			throw new AccountNotFoundException(accountId);
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
			List<Account> accounts = accountDAO.getAllAccounts();
			return accounts;
		} catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}
	}

	public boolean updateAccount(Account account) throws GeneralSystemException {
		boolean result = accountDAO.update(account);
		if (!result) {
			throw new GeneralSystemException();
		}
		
		return result;
	}

	@Override
	public Integer createNewAccount(Account account) throws GeneralSystemException {
		Integer result = accountDAO.insert(account);
		return result;
	}

	@Override
	public boolean deleteAccount(Integer accountId) throws AccountNotFoundException {

		try {
			return accountDAO.delete(accountId);
		} catch (RecordNotFoundException e) {
			throw new AccountNotFoundException(accountId);
		}
	}

}
