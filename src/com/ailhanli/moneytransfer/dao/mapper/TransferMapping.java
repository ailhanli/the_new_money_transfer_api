package com.ailhanli.moneytransfer.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ailhanli.moneytransfer.model.Transfer;

@Component
public class TransferMapping implements RowMapper<Transfer> { // sourceAccountId,destinationAccountId,

	@Override
	public Transfer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Transfer(rs.getInt("source_account_Id"), rs.getInt("destination_account_Id"), rs.getDouble("amount"),
				Currency.getInstance(rs.getString("currency")), rs.getString("comment"));
	}
}