package de.rest.RestApiProjekt;

import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import jakarta.ws.rs.core.Response;

public class KundenResourceTest {

    private static final String BASE_URL = "http://localhost:8080/";
    @BeforeAll
    public static void setUp() {

    }

    @AfterAll
    public static void tearDown() {

    }

    @Test
    public void testGetAllCustomers() {
        get(BASE_URL + "kunden")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetCustomerById() {
        int testCustomerId = 1;
        get(BASE_URL + "kunden/" + testCustomerId)
                .then()
                .statusCode(200)
                .body("id", equalTo(testCustomerId));
    }

    @Test
    public void testAddCustomer() {
        String newCustomerJson = "{\"id\":3, \"name\":\"Neuer Kunde\", \"telefonNr\":\"123456789\", \"artikelList\":null}";
        given()
                .contentType("application/json")
                .body(newCustomerJson)
                .when()
                .post(BASE_URL + "kunden")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateCustomer() {
        int testCustomerId = 1;
        String updatedCustomerJson = "{\"id\":1, \"name\":\"Aktualisierter Kunde\", \"telefonNr\":\"987654321\", \"artikelList\":null}";
        given()
                .contentType("application/json")
                .body(updatedCustomerJson)
                .when()
                .put(BASE_URL + "kunden/" + testCustomerId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Aktualisierter Kunde"));
    }

    @Test
    public void testDeleteCustomer() {
        int testCustomerId = 1;
        delete(BASE_URL + "kunden/" + testCustomerId)
                .then()
                .statusCode(200);
    }
}

