package com.ailhanli.moneytransfer.service.transfer;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.account.AccountService;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;
import com.ailhanli.moneytransfer.validator.TransferValidator;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

	private static Logger log = Logger.getLogger(TransferServiceImpl.class);

	private final TransferLogService transferLogService;
	private final AccountService accountService;
	private final TransferValidator transferValidator;

	public TransferServiceImpl(TransferLogService transferLogService, AccountService accountService,
			TransferValidator transferValidator) {
		super();
		this.transferLogService = transferLogService;
		this.accountService = accountService;
		this.transferValidator = transferValidator;
	}

	/**
	 * transferMoney methods transfers money from one account to another It
	 * firstly validates input and then validates account id if exist in DB
	 * After that atomic transfer logic starts. It firstly check balance from
	 * source account and then transfer money from one account to another During
	 * this atomic logic no other thread can enter this block until it finishes
	 * 
	 */
	@Override
	public void transferMoney(Transfer transfer) throws AccountNotFoundException, InsufficientBalanceException,
			InputInvalidException, GeneralSystemException {
		
		transferValidator.validateTransferInput(transfer);		

		Integer sourceAccountId = transfer.getSourceAccountId();
		Integer destinationAccountId = transfer.getDestinationAccountId();
		
		Account sourceAccount = accountService.getAccount(sourceAccountId);
		Account destinationAccount = accountService.getAccount(destinationAccountId);
		
		BigDecimal amountToTransfer = transfer.getAmount();
		synchronized (this) {
			try {
				//validate if it is valid amount to transfer
				transferValidator.validateTransferAmount(sourceAccount, amountToTransfer);

				//update balance for each account
				sourceAccount.withdraw(transfer.getAmount());
				destinationAccount.deposit(transfer.getAmount());

				accountService.updateAccount(sourceAccount);
				accountService.updateAccount(destinationAccount);

				// keep transfer log
				transferLogService.newTransferLog(transfer);
			} catch (Exception e) {
				log.error(e);
				throw new GeneralSystemException();
			}
		}
	}
}