package de.rest.RestApiProjekt;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BestellungResourceTest {

    private static final String BASE_URL = "http://localhost:8080/";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @AfterAll
    public static void tearDown() {
        RestAssured.reset();
    }

    @Test
    public void testGetAllArtikel() {
        get(BASE_URL + "artikel")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetArtikelById() {
        int testArtikelId = 101;
        get(BASE_URL + "artikel/" + testArtikelId)
                .then()
                .statusCode(200)
                .body("id", equalTo(testArtikelId));
    }

    @Test
    public void testAddArtikel() {
        String newArtikelJson = "{\"id\":105, \"name\":\"Neuer Artikel\", \"preis\":24.99}";
        given()
                .contentType("application/json")
                .body(newArtikelJson)
                .when()
                .post(BASE_URL + "artikel")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateArtikel() {
        int testArtikelId = 101;
        String updatedArtikelJson = "{\"id\":101, \"name\":\"Aktualisierter Artikel\", \"preis\":29.99}";
        given()
                .contentType("application/json")
                .body(updatedArtikelJson)
                .when()
                .put(BASE_URL + "artikel/" + testArtikelId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Aktualisierter Artikel"));
    }

    @Test
    public void testDeleteArtikel() {
        int testArtikelId = 105;
        delete(BASE_URL + "artikel/" + testArtikelId)
                .then()
                .statusCode(200);
    }
}

