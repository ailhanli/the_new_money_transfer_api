package com.ailhanli.moneytransfer.controller.test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

import java.util.Currency;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ailhanli.moneytransfer.model.Account;
import com.ailhanli.moneytransfer.model.Transfer;
import com.jayway.restassured.RestAssured;

public class TransferControllerTest {

	private static final String API_KEY="/api/transfer/";
	
	private static final String ACCOUNT_API_KEY="/api/accounts/";
	
	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI="http://localhost";
		RestAssured.port= 8080;
	}
	
	@AfterClass
	public static void resetSetup() {
		RestAssured.reset();
	}
	
	@Test
	public void test_successTransfer() {
		Account sourceAccount = new Account("Abdullah", 1000, Currency.getInstance("EUR"));
		Account destinationAccount = new Account("Omer", 500, Currency.getInstance("EUR"));
		
		given().body(sourceAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		given().body(destinationAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		
		Integer sourceAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Abdullah\"}.accountId");
		Integer destinationAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Omer\"}.accountId");
		
		Transfer transfer = new Transfer(sourceAccountId, destinationAccountId,100.0, Currency.getInstance("EUR"),"for transfer service test 1");
		given().body(transfer).post(API_KEY).then().assertThat().statusCode(200);
	}
}