package com.ailhanli.moneytransfer.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.properties.Config;
import com.ailhanli.moneytransfer.properties.Messages;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.util.MoneyUtil;

/**
 * Validator class for Transfer Input validation
 */
@Component
public class TransferValidator {

	private final Messages messages;
	
	private final Config config;

	public TransferValidator(Messages messages, Config config) {
		this.messages = messages;
		this.config = config;
	}

	public void validateTransferInput(Transfer transfer) throws InputInvalidException {
		
		//money to transfer should be greater than Zero.Negative or Zero amount is not allowed
		if (!(transfer.getAmount().compareTo(BigDecimal.ZERO) != 1)) {
			throw new InputInvalidException(messages.getTransferAmountInvalidMessage());
		}

		// because we don't have currency service integration we only support
		// {currencyCode} in application.properties to transfer.
		if (!MoneyUtil.INSTANCE.validateCcyCode(transfer.getCurrencyCode())
				|| !transfer.getCurrencyCode().equals(config.getCurrencyCode())) {
			throw new InputInvalidException(messages.getTransferCurrencyInvalidMessage());
		}
	}
	
	public void validateTransferAmount(Account sourceAccount, BigDecimal amoutToTransfer) throws InsufficientBalanceException{
		//Balance of source account must be greater than amountToTransfer
		BigDecimal balanceAfterTransfer = sourceAccount.getBalance().subtract(amoutToTransfer);
		if (balanceAfterTransfer.compareTo(BigDecimal.ZERO) == -1) {
			throw new InsufficientBalanceException(messages.getBalanceLimitedMessage());
		}
	}
}