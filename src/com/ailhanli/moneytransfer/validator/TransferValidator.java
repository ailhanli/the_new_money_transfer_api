package com.ailhanli.moneytransfer.validator;

import org.springframework.stereotype.Component;

import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.properties.ApplicationProperties;
import com.ailhanli.moneytransfer.properties.Messages;

/**
 * Validator class for Transfer Input validation
 */
@Component
public class TransferValidator {

	private final Messages messages;
	
	private final ApplicationProperties applicationProperties;

	public TransferValidator(Messages messages, ApplicationProperties applicationProperties) {
		this.messages = messages;
		this.applicationProperties = applicationProperties;
	}

	public boolean validateTransferInput(Transfer transfer) throws InputInvalidException {
		
		//money to transfer should be greater than Zero
		if (transfer.getAmount()<=0) {
			throw new InputInvalidException(messages.getTransferAmountInvalidMessage());
		}

		// because we don't have currency service integration we only support
		// {currencyCode} in application.properties to transfer.
		if ( !transfer.getCurrencyCode().toString().equals(applicationProperties.getCurrencyCode())) {
			throw new InputInvalidException(messages.getTransferCurrencyInvalidMessage());
		}
		
		return true;
	}
	
	public boolean validateTransferAmount(Account sourceAccount, double amoutToTransfer) throws InsufficientBalanceException{
		//Balance of source account must be greater than amountToTransfer
		double balanceAfterTransfer = sourceAccount.getBalance()-amoutToTransfer;
		if (balanceAfterTransfer<0) {
			throw new InsufficientBalanceException(messages.getBalanceLimitedMessage());
		}
		
		return true;
	}
}