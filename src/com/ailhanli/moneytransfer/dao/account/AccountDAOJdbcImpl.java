package com.ailhanli.moneytransfer.dao.account;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

public class AccountDAOJdbcImpl implements AccountDAO {

	private final String CREATE_TABLE_ACCOUNT="Create Table Account(accountId int primary key, name varchar(100), balance float, currency char(3))"
	private final String QUERY_ALL_ACCOUNTS = "Select * from Account";
	
	private final JdbcTemplate jdbcTemplate;

	public AccountDAOJdbcImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@PostConstruct
	public void createTable() {
		try {
			jdbcTemplate.update(CREATE_TABLE_ACCOUNT);
		} catch (Exception e) {
			
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		return null;
	}

	@Override
	public Account findById(Integer id) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Account account) {
		// TODO Auto-generated method stub

	}

}
