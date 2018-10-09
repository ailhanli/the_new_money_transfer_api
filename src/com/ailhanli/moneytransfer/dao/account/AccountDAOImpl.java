package com.ailhanli.moneytransfer.dao.account;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ailhanli.moneytransfer.dao.mapper.AccountMapping;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {
	private static Logger log = Logger.getLogger(AccountDAOImpl.class);

	private final String CREATE_TABLE_SQL = "Create Table Account(account_id int generated always as identity primary key, name varchar(100), balance float, currency char(3))";
	private final String QUERY_ALL_SQL = "Select * from Account";
	private final String QUERY_BY_ID_SQL = "Select * from Account where account_id=?";
	private final String UPDATE_QUERY_SQL = "Update  Account set balance=? where account_id=?";
	private final String DELETE_QUERY_SQL = "Delete from Account  where account_id=?";
	private final String INSERT_QUERY_SQL = "Insert into Account( name, balance, currency) values(?,?,?)";

	@Autowired
	private AccountMapping mapping;

	private final JdbcTemplate jdbcTemplate;

	public AccountDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	public void createTable() {
		try {
			jdbcTemplate.update(CREATE_TABLE_SQL);
		} catch (Exception e) {
			log.warn("table is already created.");
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		return jdbcTemplate.query(QUERY_ALL_SQL, mapping);
	}

	@Override
	public Account findById(Integer id) throws RecordNotFoundException {
		try {
			Account account = jdbcTemplate.queryForObject(QUERY_BY_ID_SQL, new Object[] { id }, mapping);
			return account;
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(id);
		}
	}

	@Override
	public void update(Account account) {
		jdbcTemplate.update(UPDATE_QUERY_SQL, account.getBalance(), account.getAccountId());
	}

	@Override
	public Integer insert(Account account) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_QUERY_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, account.getName());
			ps.setDouble(2, account.getBalance());
			ps.setString(3, account.getCurrency().toString());
			return ps;
		}, holder);

		return holder.getKey().intValue();
	}

	@Override
	public void delete(Integer accountId) throws RecordNotFoundException {
		findById(accountId);
		jdbcTemplate.update(DELETE_QUERY_SQL, accountId);	
	}

}