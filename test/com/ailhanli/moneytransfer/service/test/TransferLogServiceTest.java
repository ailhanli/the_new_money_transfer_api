package com.ailhanli.moneytransfer.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.dao.transferlog.TransferLogDAO;
import com.ailhanli.moneytransfer.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicaton-context-test.xml")
@Transactional
public class TransferLogServiceTest {

	private TransferLogDAO transferLogDAO;

	private TransferLogService transferLogService;

	public TransferLogServiceTest() {
		transferLogDAO = Mockito.mock(TransferLogDAO.class);
		transferLogService = new TransferLogServiceImpl(transferLogDAO);
	}

	@Test
	public void test_retrieveAllTransferLogs() throws GeneralSystemException {
		//arrange
		List<Transfer> transferLogs = new ArrayList<>();
		Transfer tr1 = new Transfer(1, 2, 650, Currency.getInstance("EUR"), "Rent");
		transferLogs.add(tr1);
		Transfer tr2 = new Transfer(0, 2, 200, Currency.getInstance("EUR"), "Happy birthday");
		transferLogs.add(tr2);
		Transfer tr3 = new Transfer(1, 0, 100, Currency.getInstance("EUR"), "Groceries");
		transferLogs.add(tr3);

		when(transferLogDAO.findAll()).thenReturn(transferLogs);

		//act
		List<Transfer> foundAllTransfer = transferLogService.getAllTransfer();
		
		//assert
		assertEquals(3, foundAllTransfer.size());
	}

	@Test
	public void test_retrieveExistingTransferLog()
			throws Exception {
		//arrange
		Integer transferId=1;
		Transfer transfer = new Transfer(transferId, 1, 2, 50, Currency.getInstance("EUR"), "Rent");
		
		when(transferLogDAO.findById(transferId)).thenReturn(transfer);

		//act
		Transfer foundTransfer = transferLogService.getTransfer(transferId);

		//assert
		assertEquals(transfer.getTransferId(), foundTransfer.getTransferId());
	}
	
	@Test
	public void test_retrieveNonExistingTransferLog()
			throws Exception {
		//arrange
		Integer transferId=1;
		Transfer transfer = new Transfer(transferId, 1, 2, 50, Currency.getInstance("EUR"), "Rent");
		
		when(transferLogDAO.findById(transferId)).thenReturn(transfer);

		//act
		Transfer foundTransfer = transferLogService.getTransfer(Integer.MAX_VALUE);

		//assert
		assertNull(foundTransfer);
	}

	@Test
	public void test_saveTransaferLog() throws Exception {
		//arrange
		Integer transferId = 7;
		Transfer transfer = new Transfer(transferId, 1, 2, 100, Currency.getInstance("EUR"), "For Test");

		when(transferLogDAO.create(transfer)).thenReturn(transferId);
		
		//act
		Integer trnasferedId = transferLogService.newTransferLog(transfer);

		//assert
		assertEquals(transferId, trnasferedId);
	}

}