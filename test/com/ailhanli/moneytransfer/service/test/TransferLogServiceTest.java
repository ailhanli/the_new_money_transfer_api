package com.ailhanli.moneytransfer.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ailhanli.moneytransfer.dao.test.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.dao.transferlog.TransferLogDAO;
import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.exception.GeneralSystemException;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.service.exception.TransferNotFoundException;
import com.ailhanli.moneytransfer.service.transferlog.TransferLogService;


public class TransferLogServiceTest {

	private TransferLogDAO transferLogDAO;
	
	private TransferLogService transferLogService;
	
	private List<Transfer> transferLogs = new ArrayList<>();
	
	@Before
	public void init() {
		
		transferLogDAO = Mockito.mock(TransferLogDAO.class);
		
		transferLogService = TransferLogService.getInstance(transferLogDAO);
		
		Transfer tr1 = new Transfer("1", "2", new BigDecimal("650"), "EUR", "Rent");
		transferLogs.add(tr1);
		Transfer tr2 = new Transfer("0", "2", new BigDecimal("200"), "EUR", "Happy birthday");
		transferLogs.add(tr2);
		Transfer tr3 = new Transfer("1", "0", new BigDecimal("100"), "EUR", "Groceries");
		transferLogs.add(tr3);
	}
	

	@Test
	public void test_retrieveAllTransferLogs() throws GeneralSystemException {
		when(transferLogDAO.findAll()).thenReturn(transferLogs);
		
		assertEquals(3, transferLogService.getAllTransfer().size());
	}
	
	@Test
	public void test_retrieveTransferLog() throws RecordNotFoundException, TransferNotFoundException, InputInvalidException, GeneralSystemException {
		Integer transactionId= 1;
		String transactionIdAsString= String.valueOf(transactionId);
		Transfer transfer = new Transfer(transactionIdAsString, "2", new BigDecimal("650"), "EUR", "Rent");
		
		when(transferLogDAO.findById(transactionId)).thenReturn(transfer);
		
		assertEquals(transfer, transferLogService.getTransfer(transactionIdAsString));
	}
	
	@Test
	public void test_saveTransaferLog() throws GeneralSystemException {
		Transfer transfer = new Transfer("1", "2", new BigDecimal("100"), "EUR", "For Test");
		Integer transferId = 7;
		
		when(transferLogDAO.create(transfer)).thenReturn(transferId);
		
		assertEquals(transferId, transferLogService.newTransferLog(transfer));
	}
	
}