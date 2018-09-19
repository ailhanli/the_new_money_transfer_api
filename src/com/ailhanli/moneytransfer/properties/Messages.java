package com.ailhanli.moneytransfer.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:messages.properties")
public class Messages {
	
	@Autowired
	private Environment environment;

	public String getAccountIdInvalidMesage() {
		return environment.getProperty("accountNumberInvalid.message");
	}
	
	public String getTransferLogIdValidationMesage() {
		return environment.getProperty("transferLogIdInvalid.message");
	}
	
	public String getTransferAmountInvalidMessage(){
		return environment.getProperty("transferAmountInvalid.message");
	}
	
	public String getTransferCurrencyInvalidMessage(){
		return environment.getProperty("currencyCodeInvalid.message");
	}
	
	public String getBalanceLimitedMessage(){
		return environment.getProperty("balanceIsLimited.message");
	}
}