package com.ailhanli.moneytransfer.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ailhanli.moneytransfer.dao.account.AccountDAO;
import com.ailhanli.moneytransfer.dao.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.service.account.AccountService;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;

public class AccountServiceTest {

	private AccountDAO accountDAO;

	private AccountService accountService;

	private List<Account> accounts = new ArrayList<>();

	@Before
	public void init() {
		accountDAO = Mockito.mock(AccountDAO.class);
		accountService = AccountService.getInstance(accountDAO);

		Account acc1 = new Account("Yana Karkov", new BigDecimal(1500), Currency.getInstance("EUR"));
		accounts.add(acc1);
		Account acc2 = new Account("Ina Karkov", new BigDecimal(300), Currency.getInstance("EUR"));
		accounts.add(acc2);
		Account acc3 = new Account("Anya Karkov", new BigDecimal(56000), Currency.getInstance("EUR"));
		accounts.add(acc3);
	}

	@Test
	public void test_retrieveAllAccounts() throws GeneralSystemException {
		when(accountDAO.getAllAccounts()).thenReturn(accounts);

		assertEquals(3, accountService.getAllAccounts().size());
	}

	@Test
	public void test_retrieveAccount()
			throws GeneralSystemException, RecordNotFoundException, AccountNotFoundException, InputInvalidException {
		Account account = new Account("Yana Karkov", new BigDecimal(1500), Currency.getInstance("EUR"));
		Integer accountId = 0;

		when(accountDAO.findById(accountId)).thenReturn(account);

		assertEquals(account, accountService.getAccount("0"));
	}

	@Test
	public void test_updateAccountBalance() throws GeneralSystemException {
		Account account = new Account("Yana Karkov", new BigDecimal(1600), Currency.getInstance("EUR"));
		Integer accountId = 0;
		BigDecimal newBalance = new BigDecimal(100);

		when(accountDAO.update(accountId, newBalance)).thenReturn(account);

		assertEquals(account, accountService.updateBalance(accountId, newBalance));
	}
}