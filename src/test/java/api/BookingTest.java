package api;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ApiDataStore;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingTest extends BaseApiTest {

    @Test(description = "API-SC-03: Verify user can create a new booking")
    public void testCreateBooking() {
        // Prepare Payload
        Map<String, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2026-10-01");
        bookingdates.put("checkout", "2026-10-15");

        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", "Gia");
        payload.put("lastname", "Huy");
        payload.put("totalprice", 1500);
        payload.put("depositpaid", true);
        payload.put("bookingdates", bookingdates);
        payload.put("additionalneeds", "Breakfast");

        // Send POST request
        Response response = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("Gia"))
                .body("booking.lastname", equalTo("Huy"))
                .extract().response();

        // Extract and Save Booking ID for Request Chaining
        int bookingId = response.path("bookingid");
        ApiDataStore.setBookingId(bookingId);
        System.out.println("Created Booking ID: " + bookingId);
    }

    @Test(description = "API-SC-04: Verify user can get booking by ID", dependsOnMethods = "testCreateBooking")
    public void testGetBookingById() {
        int bookingId = ApiDataStore.getBookingId();

        given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Gia"))
                .body("lastname", equalTo("Huy"))
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }
}
