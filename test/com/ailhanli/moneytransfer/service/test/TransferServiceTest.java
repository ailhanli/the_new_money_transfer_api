package com.ailhanli.moneytransfer.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Currency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.account.AccountService;
import com.ailhanli.moneytransfer.service.transfer.TransferService;
import com.ailhanli.moneytransfer.service.transfer.TransferServiceImpl;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;
import com.ailhanli.moneytransfer.validator.TransferValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context-test.xml")
@Transactional
public class TransferServiceTest {

	private AccountService accountService;

	private TransferLogService transferLogService;

	private TransferService transferService;

	private TransferValidator transferValidator;

	public TransferServiceTest() {
		accountService = Mockito.mock(AccountService.class);
		transferLogService = Mockito.mock(TransferLogService.class);
		transferValidator = Mockito.mock(TransferValidator.class);

		transferService = new TransferServiceImpl(transferLogService, accountService, transferValidator);
	}
	
	@Test
	public void testTransferAmount() throws Exception {
		// arrange
		Account sourceAccount= new Account(1,"Abdullah", 100, Currency.getInstance("EUR")  );
		Account destinationAccount= new Account(2,"Omer", 50, Currency.getInstance("EUR")  );
				
		Transfer transfer = new Transfer(sourceAccount.getAccountId(), destinationAccount.getAccountId(), 10, Currency.getInstance("EUR"), "For the test");
		
		when(accountService.getAccount(transfer.getSourceAccountId())).thenReturn(sourceAccount);
		when(accountService.getAccount(transfer.getDestinationAccountId())).thenReturn(destinationAccount);
		when(transferValidator.validateTransferInput(transfer)).thenReturn(Boolean.TRUE);

		// act
		transferService.transferMoney(transfer);
		
		//arrange
		assertEquals(new Double(90), Double.valueOf(sourceAccount.getBalance()));
		assertEquals(new Double(60), Double.valueOf(destinationAccount.getBalance()));

	}
}