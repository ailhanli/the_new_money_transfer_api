package com.ailhanli.moneytransfer.dao.transferlog;

import static org.junit.Assert.assertNotNull;

import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
		// arrange
		Integer transfer1Id = transferLogDAO.create(new Transfer(1, 1, 100, Currency.getInstance("EUR"), "For test"));
		Integer transfer2Id = transferLogDAO.create(new Transfer(2, 1, 200, Currency.getInstance("EUR"), "For test 2"));

		// act
		List<Transfer> transfers = transferLogDAO.findAll();

		// assert
		Transfer anyTransfer = transfers.stream()
				.filter(t -> t.getTransferId() == transfer1Id || t.getTransferId() == transfer2Id).findAny()
				.orElse(null);

		assertNotNull(anyTransfer);
	}

	@Test
	public void test_findExistingTransactionLog() throws RecordNotFoundException {
		// arrange
		int id = transferLogDAO.create(new Transfer(1, 1, 100, Currency.getInstance("EUR"), "For test"));

		// act
		Transfer transfer = transferLogDAO.findById(id);

		// assert
		assertNotNull("transfer log is not found for given id", transfer);
	}

	@Test(expected = RecordNotFoundException.class)
	public void test_findNonExistingTransactionLog() throws RecordNotFoundException {
		// arrange
		transferLogDAO.create(new Transfer(1, 1, 100, Currency.getInstance("EUR"), "For test"));

		// act
		transferLogDAO.findById(Integer.MAX_VALUE);
	}

	public void test_canCreateNewTransactionLog() throws RecordNotFoundException {
		// arrange
		Transfer transferLogToAdd = new Transfer(1, 1, 500, Currency.getInstance("EUR"), "For test");

		// act
		int id = transferLogDAO.create(transferLogToAdd);
		Transfer foundTransferLog = transferLogDAO.findById(id);

		assertNotNull("new transferlog cant created", foundTransferLog);
	}

}