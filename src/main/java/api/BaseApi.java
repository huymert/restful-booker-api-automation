package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

public abstract class BaseApi {
    protected static final String BASE_URL = ConfigReader.getProperty("base.url");
    protected static RequestSpecification requestSpec;

    static {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType("application/json")
                .setAccept("application/json")
                .build();
    }
}
