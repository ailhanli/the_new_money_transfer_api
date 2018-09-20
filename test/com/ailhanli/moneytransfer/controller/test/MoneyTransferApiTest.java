package com.ailhanli.moneytransfer.controller.test;


import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class MoneyTransferApiTest {

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
    public void test_retrieveAllAccounts() {
        final int id = get("/api/accounts").then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath().getInt("find { it.name==\"Yana Karkov\" }.accountId");
        
        get("/api/accounts/" + id).then()
                .assertThat()
                .statusCode(200)
                .body("accountId", equalTo(id))
                .body("name", equalTo("Yana Karkov"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    public void test_retrieveOneAccountPass() {
        get("/api/accounts/0").then()
                .assertThat()
                .statusCode(200)
                .body("accountId", equalTo(0))
                .body("name", equalTo("Yana Karkov"))
                .body("currency", equalTo("EUR"));
    }

    @Test
    public void test_retrieveOneAccountFail() {
        get("/api/accounts/999").then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void test_retrieveAllTransfers() {
        final int id = get("/api/transfers").then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath().getInt("find { it.amount==650 }.transferId");
        
        get("/api/transfers/" + id).then()
                .assertThat()
                .statusCode(200)
                .body("transferId", equalTo(id))
                .body("sourceAccountId", equalTo("1"))
                .body("destinationAccountId", equalTo("2"))
                .body("amount", equalTo(650))
                .body("currencyCode", equalTo("EUR"))
                .body("comment", equalTo("Rent"));
    }

    @Test
    public void test_retrieveOneTransferPass() {
        get("/api/transfers/0").then()
                .assertThat()
                .statusCode(200)
                .body("transferId", equalTo(0))
                .body("sourceAccountId", equalTo("1"))
                .body("destinationAccountId", equalTo("2"))
                .body("amount", equalTo(650))
                .body("currencyCode", equalTo("EUR"))
                .body("comment", equalTo("Rent"));
    }

    @Test
    public void test_retrieveOneTransferFail() {
        get("/api/transfers/555").then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void test_newTransferPass() {
        given().body("{\n" +
                "    \"sourceAccountId\": \"1\",\n" +
                "    \"destinationAccountId\": \"0\",\n" +
                "    \"amount\": \"100\",\n" +
                "    \"currencyCode\": \"EUR\",\n" +
                "    \"comment\": \"test transfer\"\n" +
                "}")
                .when()
                .post("api/transfers") 
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void test_newTransferFail() {
        given().body("{\n" +
                "    \"sourceAccountId\": \"1\",\n" +
                "    \"destinationAccountId\": \"0\",\n" +
                "    \"amount\": \"100\",\n" +
                "    \"currencyCode\": \"XYZ\",\n" +
                "    \"comment\": \"test transfer\"\n" +
                "}")
                .when()
                .post("api/transfers")
                .then()
                .assertThat()
                .statusCode(404);
    }
}