package com.ailhanli.moneytransfer.service.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.dao.account.AccountDAO;
import com.ailhanli.moneytransfer.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context-test.xml")
public class AccountServiceTest {

	private AccountService accountService;

	private AccountDAO accountDAO;

	public AccountServiceTest() {
		accountDAO = Mockito.mock(AccountDAO.class);
		accountService = new AccountServiceImpl(accountDAO);
	}

	@Test
	public void test_retrieveAllAccounts() throws Exception {
		//arrange
		List<Account> accounts = new ArrayList<>();
		Account acc1 = new Account("Yana Karkov", 1500, Currency.getInstance("EUR"));
		accounts.add(acc1);
		Account acc2 = new Account("Ina Karkov", 300, Currency.getInstance("EUR"));
		accounts.add(acc2);
		Account acc3 = new Account("Anya Karkov", 56000, Currency.getInstance("EUR"));
		accounts.add(acc3);

		when(accountDAO.getAllAccounts()).thenReturn(accounts);
		
		//act
		int size = accountService.getAllAccounts().size();

		//assert
		assertEquals(3, size);
	}

	@Test
	public void test_retrieveAccount() throws Exception {
		// arrange
		Integer accountId = 1;
		Account account = new Account(accountId, "Yana Karkov", 1500, Currency.getInstance("EUR"));

		// act
		when(accountDAO.findById(accountId)).thenReturn(account);

		// assert
		assertEquals(account, accountService.getAccount(accountId));
	}

	@Test
	public void test_updateAccountBalance() throws Exception {
		// arrange
		Account account = new Account(1, "Yana Karkov", 1500, Currency.getInstance("EUR"));

		// act
		accountDAO.update(account);

		// assert
		accountService.updateAccount(account);
	}
	
	@Test
	public void test_createNewAccount() throws Exception {
		// arrange
		Integer accountId = 1;
		Account account = new Account(accountId, "Yana Karkov", 1500, Currency.getInstance("EUR"));

		// act
		when(accountDAO.insert(account)).thenReturn(accountId);

		// assert
		assertEquals(accountId, accountService.createNewAccount(account));
	}
}