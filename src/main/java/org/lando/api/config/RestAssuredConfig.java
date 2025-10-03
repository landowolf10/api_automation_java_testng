package org.lando.api.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredConfig {
    private static RequestSpecification requestSpec;

    public static void init() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .addHeader("Content-Type", ContentType.JSON.toString())
                .addHeader("Accept", ContentType.JSON.toString())
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL)
                .build();

        System.out.println("🚀 Environment: " + ApiConfig.ENVIRONMENT);
        System.out.println("🔗 Base URL: " + ApiConfig.BASE_URL);
    }

    public static RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    /**
     * Method to create a custom RequestSpecification.
     * @param headers - Additional headers.
     * @return Modified RequestSpecification.
     */
    /*protected RequestSpecification getRequestWithHeaders(Map<String, String> headers) {
        return RestAssured.given()
                .spec(requestSpec)
                .headers(headers)
                .log().all();
    }*/

    /**
     * Cleaning after test suite execution
     */
    /*@AfterSuite(alwaysRun = true)
    public void tearDown() {
        System.out.println("✅ Test suite completed.");
    }*/
}
