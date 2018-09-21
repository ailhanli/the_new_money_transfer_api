package com.ailhanli.moneytransfer.service.transferlog;

import java.util.List;

import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.TransferNotFoundException;
import com.ailhanli.moneytransfer.model.Transfer;

public interface TransferLogService {

	List<Transfer> getAllTransfer()throws GeneralSystemException;

	Transfer getTransfer(Integer transferId) throws TransferNotFoundException, InputInvalidException, GeneralSystemException;

	Integer newTransferLog(Transfer transfer) throws GeneralSystemException;
}