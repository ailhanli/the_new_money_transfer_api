package com.ailhanli.moneytransfer.dao.account;

import java.util.List;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

public interface AccountDAO {
	
	List<Account> getAllAccounts();
	
	Account findById(Integer id)throws RecordNotFoundException;
	
	void update(Account account);	
	
	void delete(Integer accountId) throws RecordNotFoundException;	
	
	Integer insert(Account account);
}