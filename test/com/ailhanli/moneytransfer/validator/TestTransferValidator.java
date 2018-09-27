package com.ailhanli.moneytransfer.validator;

import java.util.Currency;

import org.junit.Test;
import org.mockito.Mockito;

import com.ailhanli.moneytransfer.exception.InputInvalidException;
import com.ailhanli.moneytransfer.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.properties.ApplicationProperties;
import com.ailhanli.moneytransfer.properties.Messages;

public class TestTransferValidator {

	private TransferValidator transferValidator;

	private final Messages messages;

	private final ApplicationProperties applicationProperties;

	public TestTransferValidator() {
		messages = Mockito.mock(Messages.class);
		applicationProperties = Mockito.mock(ApplicationProperties.class);

		transferValidator = new TransferValidator(messages, applicationProperties);
	}

	@Test(expected = InputInvalidException.class)
	public void test_validateTransferInputThrowInputInvalidExceptionForNegativeTransfer() throws Exception {

		Transfer transfer = new Transfer(1, 2, -10, Currency.getInstance("EUR"), "for test");

		transferValidator.validateTransferInput(transfer);
	}

	@Test(expected = InputInvalidException.class)
	public void test_validateTransferInputThrowInputInvalidExceptionForCurrency() throws Exception {

		Transfer transfer = new Transfer(1, 2, 100, Currency.getInstance("USD"), "for test");

		transferValidator.validateTransferInput(transfer);
	}

	@Test(expected = InsufficientBalanceException.class)
	public void test_validateTransferAmountThrowInsufficientBalanceException() throws Exception {

		Account account = new Account(1, "Abdullah", 100, Currency.getInstance("EUR"));
		double amountToTransfer = 1000;

		transferValidator.validateTransferAmount(account, amountToTransfer);
	}
}