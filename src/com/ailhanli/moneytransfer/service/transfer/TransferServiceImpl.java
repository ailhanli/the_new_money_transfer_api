package com.ailhanli.moneytransfer.service.transfer;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.account.AccountService;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;

@Service
public class TransferServiceImpl implements TransferService {
	private static Logger log = Logger.getLogger(TransferServiceImpl.class);

	private final TransferLogService transferLogService;
	private final AccountService accountService;

	public TransferServiceImpl(TransferLogService transferLogService, AccountService accountService) {
		super();
		this.transferLogService = transferLogService;
		this.accountService = accountService;
	}

	/**
	 * transferMoney methods transfers money from one account to another It firstly
	 * validates input and then validates account id if exist in DB After that
	 * atomic transfer logic starts. It firstly check balance from source account
	 * and then transfer money from one account to another During this atomic logic
	 * no other thread can enter this block until it finishes
	 * 
	 */
	@Override
	public void transferMoney(Transfer transfer) throws AccountNotFoundException, InsufficientBalanceException,
			InputInvalidException, GeneralSystemException {
		// input validation
		TransferInputValidator inputValidator = new TransferInputValidator();
		inputValidator.validateTransferInput(transfer);

		String sourceAccountId = transfer.getSourceAccountId();
		String destinationAccountId = transfer.getDestinationAccountId();
		Integer sourceAccountIdAsInteger = Integer.valueOf(sourceAccountId);
		Integer destinationAccountIdAsInteger = Integer.valueOf(destinationAccountId);

		// id validation from db
		Account sourceAccount = null, destinationAccount = null;
		try {
			sourceAccount = accountService.getAccount(sourceAccountId);
			destinationAccount = accountService.getAccount(destinationAccountId);
		} catch (AccountNotFoundException | InputInvalidException e) {
			log.warn(e);
			throw e;
		}catch (Exception e) {
			log.error(e);
			throw new GeneralSystemException();
		}

		// transfer money process
		BigDecimal balanceToTransfer = transfer.getAmount();
		synchronized (this) {
			try {

				BigDecimal balanceAfterTransfer = sourceAccount.getBalance().subtract(balanceToTransfer);
				if (balanceAfterTransfer.compareTo(BigDecimal.ZERO) == -1) {
					throw new InsufficientBalanceException(sourceAccount.getBalance());
				}

				// withdraw from source a // withdraw from source account and then deposit to
				// destination accountccount and then deposit to destination account
				sourceAccount.withdraw(transfer.getAmount());
				destinationAccount.deposit(transfer.getAmount());

				// update accounts balance
				accountService.updateBalance(sourceAccountIdAsInteger, sourceAccount.getBalance());
				accountService.updateBalance(destinationAccountIdAsInteger, destinationAccount.getBalance());

				// keep transfer log
				transferLogService.newTransferLog(transfer);
			} catch (Exception e) {
				log.error(e);
				throw new GeneralSystemException();
			}
		}
	}
}