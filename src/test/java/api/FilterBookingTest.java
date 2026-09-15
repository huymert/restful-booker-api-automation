package api;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ApiDataStore;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class FilterBookingTest extends BaseApiTest {

    @Test(description = "API-SC-09: Filter booking by firstname and lastname", dependsOnMethods = "api.BookingTest.testCreateBooking")
    public void testFilterByNames() {
        given()
                .spec(requestSpec)
                .queryParam("firstname", "Gia")
                .queryParam("lastname", "Huy")
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Should return at least the one we just created
    }

    @Test(description = "API-SC-10: Filter booking by checkin and checkout dates", dependsOnMethods = "api.BookingTest.testCreateBooking")
    public void testFilterByDates() {
        given()
                .spec(requestSpec)
                .queryParam("checkin", "2026-09-01")
                .queryParam("checkout", "2026-10-31")
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
