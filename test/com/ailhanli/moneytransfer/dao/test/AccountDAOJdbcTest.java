package com.ailhanli.moneytransfer.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.dao.account.AccountDAO;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context-test.xml")
@Transactional
public class AccountDAOJdbcTest {
		
	@Autowired
	private AccountDAO accountDAO;
	
	@Test
	public void test_CanGetAllAccounts() {
		
		//arrange
		accountDAO.insert(new Account( "Abdullah", 100, Currency.getInstance("EUR")));
		accountDAO.insert(new Account( "Omer", 350, Currency.getInstance("EUR")));
		
		//act
		List<Account> accounts = accountDAO.getAllAccounts();
		
		//assert
		assertEquals(2, accounts.size());
	}
	
	
	@Test
	public void test_RetrieveById() {
		
		//arrange
		Integer id = accountDAO.insert(new Account( "Johny", 100, Currency.getInstance("EUR")));
		
		//act
		try {
			Account foundAccount = accountDAO.findById(id);
			System.out.println(foundAccount);
			
			//assert
			assertEquals(id, foundAccount.getAccountId());
		} catch (RecordNotFoundException e) {
			fail("no account was found even one exist");
		}
	}
	
	//@Test(expected=RecordNotFoundException.class)
	public void test_findingNonExistAccount() throws RecordNotFoundException {
		accountDAO.findById(9999);
	}
	
	@Test
	public void test_updateAccount() throws RecordNotFoundException {
		//arrange
		Integer id = accountDAO.insert(new Account( "Johny", 100.50, Currency.getInstance("EUR")));
		Account accountToUpdate = accountDAO.findById(id);
		accountToUpdate.withdraw(20);
		
		//act
		accountDAO.update(accountToUpdate);
		Account lastStateOfAccount = accountDAO.findById(id);
		
		//assert
		assertEquals(new Double(80.5), new Double(lastStateOfAccount.getBalance()));
	}
	
	@Test
	public void test_canWeAddNewAccount() throws RecordNotFoundException {
		//arrange
		Account accountToInsert = new Account("AccountToTest", 500, Currency.getInstance("EUR"));
		
		//act
		Integer accountId= accountDAO.insert(accountToInsert);
		Account foundAccount = accountDAO.findById(accountId);
		
		//assert
		assertNotNull(foundAccount);
	}
}