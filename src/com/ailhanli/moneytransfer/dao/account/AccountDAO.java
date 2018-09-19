package com.ailhanli.moneytransfer.dao.account;

import java.math.BigDecimal;
import java.util.List;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

public interface AccountDAO {
	
	List<Account> getAllAccounts();
	
	Account findById(Integer id)throws RecordNotFoundException;
	
	Account update(Integer accountId, BigDecimal balanceToTransfer);
}