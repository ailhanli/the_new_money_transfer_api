package com.ailhanli.moneytransfer.dao.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

	private final Map<Integer, Account> accountDB = new LinkedHashMap<>();
	
	public AccountDAOImpl() {
		Account acc1 = new Account("Yana Karkov", new BigDecimal(1500), Currency.getInstance("EUR"));
		accountDB.put(acc1.getAccountId(), acc1);
		Account acc2 = new Account("Ina Karkov", new BigDecimal(300), Currency.getInstance("EUR"));
		accountDB.put(acc2.getAccountId(), acc2);
		Account acc3 = new Account("Anya Karkov", new BigDecimal(56000), Currency.getInstance("EUR"));
		accountDB.put(acc3.getAccountId(), acc3);

	}

	@Override
	public Account findById(Integer id) throws RecordNotFoundException {
		Account account = accountDB.get(id);
		if (account == null) {
			throw new RecordNotFoundException(id);
		}
		return account;
	}

	@Override
	public void update(Account account){
		accountDB.put(account.getAccountId(), account);
	}

	@Override
	public List<Account> getAllAccounts() {
		return new ArrayList<>(accountDB.values());
	}
}