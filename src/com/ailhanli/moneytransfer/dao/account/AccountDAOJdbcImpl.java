package com.ailhanli.moneytransfer.dao.account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Currency;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.service.account.AccountServiceImpl;

@Repository
public class AccountDAOJdbcImpl implements AccountDAO {
	private static Logger log = Logger.getLogger(AccountDAOJdbcImpl.class);

	private final String CREATE_TABLE_ACCOUNT = "Create Table Account(accountId int generated always as identity primary key, name varchar(100), balance float, currency char(3))";
	private final String QUERY_ALL_ACCOUNTS = "Select * from Account";
	private final String QUERY_ACCOUNT_BY_ID = "Select * from Account where accountId=?";
	private final String UPDATE_ACCOUNT_BALANCE = "Update  Account set balance=? where accountId=?";
	private final String INSERT_ACCOUNT = "Insert into Account( name, balance, currency) values(?,?,?)";

	private final JdbcTemplate jdbcTemplate;

	public AccountDAOJdbcImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	public void createTable() {
		try {
			jdbcTemplate.update(CREATE_TABLE_ACCOUNT);
		} catch (Exception e) {
			log.warn("table is already created.");
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		return jdbcTemplate.query(QUERY_ALL_ACCOUNTS, new AccountMapping());
	}

	@Override
	public Account findById(Integer id) throws RecordNotFoundException {
		try {
			Account account=  jdbcTemplate.queryForObject(QUERY_ACCOUNT_BY_ID, new Object[] {id}, new AccountMapping());
			return account;
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(id);
		}
	}

	@Override
	public void update(Account account) {
		jdbcTemplate.update(UPDATE_ACCOUNT_BALANCE, account.getBalance(), account.getAccountId());
	}

	@Override
	public Integer insert(Account account) {

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, account.getName());
			ps.setDouble(2, account.getBalance());
			ps.setString(3, account.getCurrency().toString());
			return ps;
		}, holder);

		return holder.getKey().intValue();
	}

}

class AccountMapping implements RowMapper<Account> {
	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Account(rs.getInt("accountId"), rs.getString("name"), rs.getDouble("balance"),
				Currency.getInstance(rs.getString("currency")));
	}
}