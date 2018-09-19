package com.ailhanli.moneytransfer.dao.transferlog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Transfer;

@Repository
public class TransferLogDAOImpl implements TransferLogDAO {

	private final Map<Integer, Transfer> transferDB = new LinkedHashMap<>();
	
	private TransferLogDAOImpl() {
		Transfer tr1 = new Transfer("1", "2", new BigDecimal("650"), "EUR", "Rent");
		transferDB.put(tr1.getTransferId(), tr1);
		Transfer tr2 = new Transfer("0", "2", new BigDecimal("200"), "EUR", "Happy birthday");
		transferDB.put(tr2.getTransferId(), tr2);
		Transfer tr3 = new Transfer("1", "0", new BigDecimal("100"), "EUR", "Groceries");
		transferDB.put(tr2.getTransferId(), tr3);
	}

	@Override
	public List<Transfer> findAll() {
		return new ArrayList<>(transferDB.values());
	}

	@Override
	public Transfer findById(Integer id) throws RecordNotFoundException {
		Transfer transfer = transferDB.get(id);
		if (transfer == null) {
			throw new RecordNotFoundException(id);
		}

		return transfer;
	}

	@Override
	public Integer create(Transfer transfer) {
		transferDB.put(transfer.getTransferId(), transfer);
		return transfer.getTransferId();
	}
}
