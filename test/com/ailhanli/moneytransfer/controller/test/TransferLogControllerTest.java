package com.ailhanli.moneytransfer.controller.test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.Currency;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ailhanli.moneytransfer.model.Transfer;
import com.jayway.restassured.RestAssured;

public class TransferLogControllerTest {

	private static final String API_URI="/api/transferLogs/";
	
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
	public void test_canGetAllTransfers() {
		Transfer transfer1 = new Transfer(1, 0, 100.0, Currency.getInstance("EUR"), "for http test");
		given().body(transfer1).post(API_URI).then().assertThat().statusCode(200);
		Transfer transfer2 = new Transfer(1, 0, 100.0, Currency.getInstance("EUR"), "for http test");
		given().body(transfer2).post(API_URI).then().assertThat().statusCode(200);
		
		Integer transferId = get(API_URI).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.comment==\"for http test\"}.transferId");
		
		get(API_URI+transferId).then().assertThat().statusCode(200).body("transferId", equalTo(transferId));
	}
	
	@Test
	public void test_retrieveOneExistingTransfer() {
		Transfer transfer1 = new Transfer(1, 0, 450, Currency.getInstance("EUR"), "for http test");
		given().body(transfer1).post(API_URI).then().assertThat().statusCode(200);
		
		Integer transferId = get(API_URI).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.comment==\"for http test\"}.transferId");
		
		get(API_URI+transferId).then().assertThat().statusCode(200);
	}
	
	@Test
	public void test_retrieveNoneExistingTransfer() {
		get(API_URI+999).then().assertThat().statusCode(404);
	}
	
	@Test
	public void test_canCreateNewTransferLog() {
		Transfer transfer1 = new Transfer(1, 0, 450, Currency.getInstance("EUR"), "for http test");
		
		given().body(transfer1).post(API_URI).then().assertThat().statusCode(200);
	}
}