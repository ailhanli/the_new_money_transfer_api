package com.ailhanli.moneytransfer.service.transfer;

import java.math.BigDecimal;

import com.ailhanli.moneytransfer.model.Transfer;
import com.ailhanli.moneytransfer.service.exception.InputInvalidException;
import com.ailhanli.moneytransfer.util.MoneyUtil;

/**
 * Validator class for Transfer Input validation
 */
public class TransferInputValidator {

	public void validateTransferInput(Transfer transfer) throws InputInvalidException {
		if(!(transfer.getAmount().compareTo(BigDecimal.ONE) == 1)) {
			throw new InputInvalidException("Transfer amount is zero or negative. It must be greater than zero");
		}
		
		
		// because we don't have currency service integration we only support EURO transfer.
		if(!MoneyUtil.INSTANCE.validateCcyCode(transfer.getCurrencyCode()) || !transfer.getCurrencyCode().equals("EUR") ) {
			throw new InputInvalidException("Currency code is invalid. It must be a currency code EUR.");
		}		
	}
}