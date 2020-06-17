package com.koalatechie.app;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
//import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AdminResourceTest {

    @Test
    public void testAdminEndpoint() {
        given()
          .when().get("/admin")
          .then()
             .statusCode(200)
             .body(containsString("ADMIN"));
    }

}