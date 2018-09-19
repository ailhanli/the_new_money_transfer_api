package com.ailhanli.moneytransfer.service.test;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.account.AccountService;
import com.ailhanli.moneytransfer.service.exception.AccountNotFoundException;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.InsufficientBalanceException;
import com.ailhanli.moneytransfer.service.transfer.TransferService;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;

public class TransferServiceTest {
	
	private AccountService accountService;

	private TransferLogService transferLogService;

	private TransferService transferService;

	@Before
	public void initTransferService() throws AccountNotFoundException, InputInvalidException, GeneralSystemException,
			InsufficientBalanceException {
		accountService = Mockito.mock(AccountService.class);
		transferLogService = Mockito.mock(TransferLogService.class);
		transferService = TransferService.getInstance(transferLogService, accountService);
	}

	@Test
	public void test_transferMoney() throws AccountNotFoundException, InputInvalidException, GeneralSystemException,
			InsufficientBalanceException {

		when(accountService.getAccount("0")).thenReturn(new Account("Yana Karkov", new BigDecimal(1500), Currency.getInstance("EUR")));
		when(accountService.getAccount("1")).thenReturn(new Account("Ina Karkov", new BigDecimal(300), Currency.getInstance("EUR")));

		transferService.transferMoney(new Transfer("0", "1", new BigDecimal(100), "EUR", "unit-test"));
	}
}