package com.koalatechie.app;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ArtistaResourceTest {

	@Test
    public void artistaGetEndPoint() {
        given()
          .when().get("/artista")
          .then()
             .statusCode(200)
             .contentType("application/json");
    }
}
