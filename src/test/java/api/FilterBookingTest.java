package api;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ApiDataStore;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class FilterBookingTest extends BaseApiTest {

    private String filterFirstname;
    private String filterLastname;
    private String filterCheckin;
    private String filterCheckout;

    @BeforeClass
    public void createBookingForFilter() {
        // Create a dedicated booking for filter tests (independent from BookingTest)
        filterFirstname = "FilterHa";
        filterLastname = "TestAuto";
        filterCheckin = "2027-01-01";
        filterCheckout = "2027-01-15";

        Map<String, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", filterCheckin);
        bookingdates.put("checkout", filterCheckout);

        Map<String, Object> payload = new HashMap<>();
        payload.put("firstname", filterFirstname);
        payload.put("lastname", filterLastname);
        payload.put("totalprice", 999);
        payload.put("depositpaid", true);
        payload.put("bookingdates", bookingdates);
        payload.put("additionalneeds", "Wifi");

        Response response = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .extract().response();
    }

    @Test(description = "API-SC-09: Filter booking by firstname and lastname")
    public void testFilterByNames() {
        given()
                .spec(requestSpec)
                .queryParam("firstname", filterFirstname)
                .queryParam("lastname", filterLastname)
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(description = "API-SC-10: Filter booking by checkin and checkout dates")
    public void testFilterByDates() {
        given()
                .spec(requestSpec)
                .queryParam("checkin", "2026-12-01")
                .queryParam("checkout", "2027-02-01")
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
