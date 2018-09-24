package com.ailhanli.moneytransfer.service.transferlog;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailhanli.moneytransfer.dao.transferlog.TransferLogDAO;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.exception.TransferNotFoundException;
import com.ailhanli.moneytransfer.model.Transfer;

@Service
public class TransferLogServiceImpl implements TransferLogService {
	private static Logger log = Logger.getLogger(TransferLogServiceImpl.class);

	private final TransferLogDAO logDAO;

	public TransferLogServiceImpl(TransferLogDAO logDAO) {
		super();
		this.logDAO = logDAO;
	}

	@Override
	public List<Transfer> getAllTransfer() throws GeneralSystemException {
		try {
			List<Transfer> transfers = logDAO.findAll();
			return transfers;
		} catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}
	}

	@Override
	public Transfer getTransfer(Integer transferId)
			throws TransferNotFoundException, GeneralSystemException {

		try {
			return logDAO.findById(transferId);
		} catch (RecordNotFoundException e) {
			log.warn(e);
			throw new TransferNotFoundException(e.getRecordId());
		} catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}
	}

	@Override
	public Integer newTransferLog(Transfer transfer) throws GeneralSystemException {
		return logDAO.create(transfer);
	}
}