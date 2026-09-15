package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiDataStore;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTest extends BaseApiTest {

    @Test(description = "API-SC-01: Verify user can generate auth token with valid credentials")
    public void testCreateTokenSuccess() {
        Map<String, String> authPayload = new HashMap<>();
        authPayload.put("username", ConfigReader.getProperty("api.username"));
        authPayload.put("password", ConfigReader.getProperty("api.password"));

        Response response = given()
                .spec(requestSpec)
                .body(authPayload)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        String token = response.path("token");
        ApiDataStore.setAuthToken(token);
    }

    @Test(description = "API-SC-02: Verify auth fails with invalid credentials")
    public void testCreateTokenInvalidCredentials() {
        Map<String, String> authPayload = new HashMap<>();
        authPayload.put("username", "admin");
        authPayload.put("password", "wrongpassword");

        given()
                .spec(requestSpec)
                .body(authPayload)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}
