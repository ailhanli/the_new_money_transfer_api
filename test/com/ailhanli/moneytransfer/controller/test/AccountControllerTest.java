package com.ailhanli.moneytransfer.controller.test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Currency;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ailhanli.moneytransfer.model.Account;
import com.jayway.restassured.RestAssured;

public class AccountControllerTest {

	private static final String API_URI="/api/accounts/";
	
	@BeforeClass
	public static void configureRestAssured() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@AfterClass
	public static void unconfigureRestAssured() {
		RestAssured.reset();
	}

	@Test
	public void test_create_retfieveAll_retriveAccount_In_S() {
		//firstly create two dummy accounts
		Account account = new Account("Account1",Double.valueOf(100), Currency.getInstance("EUR"));
		Account account2 = new Account("Account2",Double.valueOf(500), Currency.getInstance("EUR"));
		given().body(account).post(API_URI).then().assertThat().statusCode(200);
		given().body(account2).post(API_URI).then().assertThat().statusCode(200);
		
		//and then get one of account id which is created above
		int accountId = get(API_URI).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Account1\"}.accountId");
		
		//check if it is exist
		get(API_URI+accountId).then().assertThat().statusCode(200).body("accountId", equalTo(accountId));	
	}
	
	@Test
	public void test_retvieveExistingAccount() {
		//create new dummy account for test
		Account account = new Account("Account5",Double.valueOf(1500), Currency.getInstance("EUR"));
		given().body(account).post(API_URI).then().assertThat().statusCode(200);
		
		//get account id for the retrieve method
		int accountId = get(API_URI).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Account5\"}.accountId");
		
		//retrieve account and then verify the name
		get(API_URI+accountId).then().assertThat().statusCode(200).body("name", equalTo("Account5"));
	}
	
	@Test
	public void test_retrieveNonExistingAccount() {
		//get non existing account. we expect 404
		get(API_URI+"9999").then().assertThat().statusCode(404);
	}

	@Test
	public void test_canCreateNewAccount() {
		Account account = new Account("Account3",Double.valueOf(1000), Currency.getInstance("EUR"));
		
		//send post in order to create new account
		given().body(account).post(API_URI).then().assertThat().statusCode(200);
	}
	
	@Test
	public void test_canDeleteAccount() {
		Account account = new Account("Account4",Double.valueOf(1000), Currency.getInstance("EUR"));
		
		//first create an account and then return its id
		given().body(account).post(API_URI).then().assertThat().statusCode(200);
		int accountId = get(API_URI).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.name==\"Account4\"}.accountId");
		
		//delete account by id
		given().body(accountId).delete(API_URI).then().assertThat().statusCode(200);
	}
}