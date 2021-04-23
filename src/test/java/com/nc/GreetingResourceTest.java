package com.nc;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello-resteasy")
          .then()
             .statusCode(200)
             .body(is("Say hello,dear Diana"));
    }

    @Test
    public void testWithDate(){
        given().when().get("/hello-resteasy/22-04-2021")
                .then().statusCode(200)
                .body(is("Say hello,dear Diana,date: 22-04-2021"));
    }

}