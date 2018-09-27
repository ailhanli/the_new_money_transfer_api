package com.ailhanli.moneytransfer.controller.transfer;

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

	private static final String API_KEY="/api/transfers/";
	
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
		//creating source and destination account for transfer
		Account sourceAccount = new Account("Abdullah", 1000, Currency.getInstance("EUR"));
		Account destinationAccount = new Account("Omer", 500, Currency.getInstance("EUR"));
		
		given().body(sourceAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		given().body(destinationAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		
		//getting source and destination account id
		Integer sourceAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Abdullah\"}.accountId");
		Integer destinationAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Omer\"}.accountId");
		
		//transfer api call
		Transfer transfer = new Transfer(sourceAccountId, destinationAccountId,100.0, Currency.getInstance("EUR"),"for transfer service test 1");
		given().body(transfer).post(API_KEY).then().assertThat().statusCode(200);
	}
	
	@Test
	public void test_TransferForNonExistingAccount() {	
		//testing for non existing account
		Transfer transfer = new Transfer(87, 1,100.0, Currency.getInstance("EUR"),"for transfer service test 2");
		given().body(transfer).post(API_KEY).then().assertThat().statusCode(404);
	}
	
	@Test
	public void test_TransferForInsufficientBalance() {	
		//creating source and destination account for transfer
		Account sourceAccount = new Account("Abdullah", 1000, Currency.getInstance("EUR"));
		Account destinationAccount = new Account("Omer", 500, Currency.getInstance("EUR"));
		
		given().body(sourceAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		given().body(destinationAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		
		//getting source and destination account id
		Integer sourceAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Abdullah\"}.accountId");
		Integer destinationAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Omer\"}.accountId");
		
		//transfer insufficient balance api call
		Transfer transfer = new Transfer(sourceAccountId, destinationAccountId,9999.0, Currency.getInstance("EUR"),"for transfer service test 1");
		given().body(transfer).post(API_KEY).then().assertThat().statusCode(404);
	}
	
	@Test
	public void test_TransferForNegativeTransfer() {
		//creating source and destination account for transfer
		Account sourceAccount = new Account("Abdullah", 1000, Currency.getInstance("EUR"));
		Account destinationAccount = new Account("Omer", 500, Currency.getInstance("EUR"));
		
		given().body(sourceAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		given().body(destinationAccount).post(ACCOUNT_API_KEY).then().assertThat().statusCode(200);
		
		//getting source and destination account id
		Integer sourceAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Abdullah\"}.accountId");
		Integer destinationAccountId = get(ACCOUNT_API_KEY).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Omer\"}.accountId");
		
		//transfer negative amount api call
		Transfer transfer = new Transfer(sourceAccountId, destinationAccountId,-100.0, Currency.getInstance("EUR"),"for transfer service test 1");
		given().body(transfer).post(API_KEY).then().assertThat().statusCode(404);
	}
}