package com.ailhanli.moneytransfer.service.transferlog;

import java.util.List;

import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.TransferNotFoundException;

public interface TransferLogService {

	List<Transfer> getAllTransfer()throws GeneralSystemException;

	Transfer getTransfer(String transferId) throws TransferNotFoundException, InputInvalidException, GeneralSystemException;

	Integer newTransferLog(Transfer transfer) throws GeneralSystemException;
}