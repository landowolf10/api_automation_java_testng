package org.lando.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.lando.config.APIConstants;

public class RestAssuredClient {
    public void setupRestAssured() {
        RestAssured.baseURI = APIConstants.BASE_URL;
        //RestAssured.basePath = BASE_PATH;

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", ContentType.JSON.toString())
                .addHeader("Accept", ContentType.JSON.toString())
                .build();
    }
}
