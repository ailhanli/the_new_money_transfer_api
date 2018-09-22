package com.ailhanli.moneytransfer.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ailhanli.moneytransfer.model.Account;

@Component
public class AccountMapping implements RowMapper<Account> {
	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Account(rs.getInt("account_Id"), rs.getString("name"), rs.getDouble("balance"),
				Currency.getInstance(rs.getString("currency")));
	}
}