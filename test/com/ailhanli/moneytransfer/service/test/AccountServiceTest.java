package com.ailhanli.moneytransfer.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.dao.account.AccountDAO;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.service.account.AccountService;
import com.ailhanli.moneytransfer.service.account.AccountServiceImpl;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicaton-context-test.xml")
@Transactional
public class AccountServiceTest {

	private AccountService accountService;

	private AccountDAO accountDAO;

	public AccountServiceTest() {
		accountDAO = Mockito.mock(AccountDAO.class);
		accountService = new AccountServiceImpl(accountDAO);
	}

	@Test
	public void test_retrieveAllAccounts() throws GeneralSystemException {

		List<Account> accounts = new ArrayList<>();
		Account acc1 = new Account("Yana Karkov", 1500, Currency.getInstance("EUR"));
		accounts.add(acc1);
		Account acc2 = new Account("Ina Karkov", 300, Currency.getInstance("EUR"));
		accounts.add(acc2);
		Account acc3 = new Account("Anya Karkov", 56000, Currency.getInstance("EUR"));
		accounts.add(acc3);

		when(accountDAO.getAllAccounts()).thenReturn(accounts);

		assertEquals(3, accountService.getAllAccounts().size());
	}

	@Test
	public void test_retrieveAccount()
			throws GeneralSystemException, RecordNotFoundException, AccountNotFoundException, InputInvalidException {
		//arrange
		Integer accountId = 1;
		Account account = new Account(accountId, "Yana Karkov", 1500, Currency.getInstance("EUR"));

		//act
		when(accountDAO.findById(accountId)).thenReturn(account);

		//assert
		assertEquals(account, accountService.getAccount(accountId));
	}

	@Test
	public void test_updateAccountBalance() throws GeneralSystemException {
		Account account = new Account("Yana Karkov", 1600, Currency.getInstance("EUR"));
		Integer accountId = 0;
		double newBalance = 100;

		when(accountDAO.update(accountId, newBalance)).thenReturn(account);

		assertEquals(account, accountService.updateBalance(accountId, newBalance));
	}
}