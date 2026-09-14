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
    @Test(
            description = "API-SC-05: Verify user can fully update a booking",
            dependsOnMethods = "testGetBookingById"
    )
    public void testUpdateBooking() {
        int bookingId = ApiDataStore.getBookingId();
        String token = ApiDataStore.getAuthToken();

        // Prepare updated booking dates
        Map<String, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2026-11-01");
        bookingdates.put("checkout", "2026-11-10");

        // Prepare full update payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", "Quynh Chi");
        payload.put("lastname", "Tran");
        payload.put("totalprice", 300);
        payload.put("depositpaid", false);
        payload.put("bookingdates", bookingdates);
        payload.put("additionalneeds", "Lunch");

        given()
                .spec(requestSpec)
                .cookie("token", token)
                .body(payload)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Quynh Chi"))
                .body("lastname", equalTo("Tran"))
                .body("totalprice", equalTo(300))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2026-11-01"))
                .body("bookingdates.checkout", equalTo("2026-11-10"))
                .body("additionalneeds", equalTo("Lunch"));
    }
    @Test(
            description = "API-SC-06: Verify user can partially update a booking",
            dependsOnMethods = "testUpdateBooking"
    )
    public void testPartialUpdateBooking() {
        int bookingId = ApiDataStore.getBookingId();
        String token = ApiDataStore.getAuthToken();

        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", "Minh Huy");

        given()
                .spec(requestSpec)
                .cookie("token", token)
                .body(payload)
                .when()
                .patch("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Minh Huy"))
                .body("lastname", equalTo("Tran"))
                .body("totalprice", equalTo(300))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2026-11-01"))
                .body("bookingdates.checkout", equalTo("2026-11-10"))
                .body("additionalneeds", equalTo("Lunch"));
    }
    @Test(
            description = "API-SC-07: Verify user can delete a booking",
            dependsOnMethods = "testPartialUpdateBooking"
    )
    public void testDeleteBooking() {
        int bookingId = ApiDataStore.getBookingId();
        String token = ApiDataStore.getAuthToken();

        given()
                .spec(requestSpec)
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201);
    }
    @Test(
            description = "API-SC-08: Verify deleted booking cannot be retrieved",
            dependsOnMethods = "testDeleteBooking"
    )
    public void testGetDeletedBooking() {
        int bookingId = ApiDataStore.getBookingId();

        given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(404);
    }
}
