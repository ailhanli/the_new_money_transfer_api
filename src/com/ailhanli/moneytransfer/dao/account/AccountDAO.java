package com.ailhanli.moneytransfer.dao.account;

import java.util.List;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

public interface AccountDAO {
	
	List<Account> getAllAccounts();
	
	Account findById(Integer id)throws RecordNotFoundException;
	
	boolean update(Account account);	
	
	Integer insert(Account account);
}