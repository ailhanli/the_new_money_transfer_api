package com.ailhanli.moneytransfer.dao.transferlog;

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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Transfer;

@Repository
public class TransferLogDAOImpl implements TransferLogDAO {
	private static Logger log = Logger.getLogger(TransferLogDAOImpl.class);

	private final String CREATE_TABLE = "Create Table Transfer(transfer_Id int generated always as identity primary key, source_account_id int, destination_account_id int, amount float, currency varchar(3), comment varchar(200))";

	private final String QUERY_ALL_ACCOUNTS = "Select * from Transfer";

	private final String QUERY_FIND_BY_ID = "Select * from Transfer where transfer_id=?";

	private final String INSERT_TRANSFER_QUERY = "Insert into Transfer(source_account_id,destination_account_id, amount, currency, comment ) values(?,?,?,?,?)";

	private JdbcTemplate jdbcTemplate;

	public TransferLogDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	public void createTable() {
		try {
			jdbcTemplate.update(CREATE_TABLE);
		} catch (Exception e) {
			log.warn("table is already created.");
		}
	}

	@Override
	public List<Transfer> findAll() {
		return jdbcTemplate.query(QUERY_ALL_ACCOUNTS, new TransferMapping());
	}

	@Override
	public Transfer findById(Integer id) throws RecordNotFoundException {
		try {
			Transfer transfer = jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, new Object[] { id },
					new TransferMapping());
			return transfer;
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException(id);
		}
	}

	@Override
	public Integer create(Transfer transfer) {
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_TRANSFER_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, transfer.getSourceAccountId());
			ps.setInt(2, transfer.getDestinationAccountId());
			ps.setDouble(3, transfer.getAmount());
			ps.setString(4, transfer.getCurrencyCode().toString());
			ps.setString(5, transfer.getComment());
			return ps;
		}, holder);

		return holder.getKey().intValue();
	}
}

class TransferMapping implements RowMapper<Transfer> { // sourceAccountId,destinationAccountId,
														// amount, currencyCode,
														// comment
	@Override
	public Transfer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Transfer(rs.getInt("source_account_Id"), rs.getInt("destination_account_Id"), rs.getDouble("amount"),
				Currency.getInstance(rs.getString("currency")), rs.getString("comment"));
	}
}