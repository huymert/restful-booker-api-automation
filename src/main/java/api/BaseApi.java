package api;

import io.restassured.RestAssured;

public abstract class BaseApi {
    protected static final String BASE_URL = "https://restful-booker.herokuapp.com";

    static {
        RestAssured.baseURI = BASE_URL;
    }
}
