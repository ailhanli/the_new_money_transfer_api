package com.ailhanli.moneytransfer.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ailhanli.moneytransfer.dao.transferlog.TransferLogDAO;
import com.ailhanli.moneytransfer.exception.RecordNotFoundException;
import com.ailhanli.moneytransfer.model.Transfer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context-test.xml")
@Transactional
public class TransferLogDAOTest {

	
	@Autowired
	private TransferLogDAO transferLogDAO;
	
	@Test
	public void test_getAllTransferLogs() {
		//arrange
		transferLogDAO.create(new Transfer(1, 1, 100, Currency.getInstance("EUR"), "For test"));
		transferLogDAO.create(new Transfer(2, 1, 200, Currency.getInstance("EUR"), "For test 2"));
	
		//act
		List<Transfer> transfers= transferLogDAO.findAll();
		
		//assert
		assertEquals(2, transfers.size());
	}
	
	@Test
	public void test_findExistingTransactionLog() throws RecordNotFoundException {
		//arrange
		int id =transferLogDAO.create(new Transfer(1, 1, 100, Currency.getInstance("EUR"), "For test"));
		
		//act
		Transfer transfer = transferLogDAO.findById(id);
		
		//assert
		assertNotNull(transfer);
	}
	
	@Test(expected= RecordNotFoundException.class)
	public void test_findNonExistingTransactionLog() throws RecordNotFoundException {
		//arrange
		transferLogDAO.create(new Transfer(1, 1, 100, Currency.getInstance("EUR"), "For test"));
		
		//act
		transferLogDAO.findById(Integer.MAX_VALUE);
	}
	
	public void test_canCreateNewTransactionLog() throws RecordNotFoundException {
		//arrange
		Transfer transferLogToAdd = new Transfer(1, 1, 500, Currency.getInstance("EUR"), "For test");
		
		//act
		int id =transferLogDAO.create(transferLogToAdd);
		Transfer foundTransferLog = transferLogDAO.findById(id);
		
		assertNotNull(foundTransferLog);
	}
	
}