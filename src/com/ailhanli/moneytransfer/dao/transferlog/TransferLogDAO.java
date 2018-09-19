package com.ailhanli.moneytransfer.dao.transferlog;

import java.util.List;

import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Transfer;

public interface TransferLogDAO {

	List<Transfer> findAll();

	Transfer findById(Integer id) throws RecordNotFoundException;

	Integer create(Transfer transfer);
}