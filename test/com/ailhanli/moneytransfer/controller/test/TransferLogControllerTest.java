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
	public void test_CanGetAllTransfers() {
		Transfer transfer1 = new Transfer(1, 0, Double.valueOf(100), Currency.getInstance("EUR"), "for http test");
		Transfer transfer2 = new Transfer(1, 2, 100, Currency.getInstance("EUR"), "for http test2");
		given().body(transfer1).post(API_URI).then().assertThat().statusCode(200);
		given().body(transfer2).post(API_URI).then().assertThat().statusCode(200);
		
		Integer transferId = get(API_URI).then().assertThat().statusCode(200).extract().jsonPath().getInt("find {it.comment==\"for http test\"}.transferId");
		
		get(API_URI+transferId).then().assertThat().statusCode(200).body("sourceAccountId", equalTo(1)).body("destinationAccountId", equalTo(2)).body("amount", equalTo(100));
	}
}