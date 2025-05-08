package org.lando.api.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestAssuredConfig {
    protected static RequestSpecification requestSpec;

    public void setupRestAssured() {
        //RestAssured.basePath = BASE_PATH;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .addHeader("Content-Type", ContentType.JSON.toString())
                .addHeader("Accept", ContentType.JSON.toString())
                .setRelaxedHTTPSValidation() // Para entornos con certificados auto-firmados
                .log(LogDetail.ALL)
                .build();

        System.out.println("🚀 Environment: " + ApiConfig.ENVIRONMENT);
        System.out.println("🔗 Base URL: " + ApiConfig.BASE_URL);
    }

    /**
     * Método para crear una RequestSpecification personalizada.
     * @param headers - Headers adicionales.
     * @return RequestSpecification modificada.
     */
    protected RequestSpecification getRequestWithHeaders(Map<String, String> headers) {
        return RestAssured.given()
                .spec(requestSpec)
                .headers(headers)
                .log().all();
    }

    /**
     * Limpieza después de ejecutar la suite de pruebas.
     */
    /*@AfterSuite(alwaysRun = true)
    public void tearDown() {
        System.out.println("✅ Suite de pruebas completada.");
    }*/
}
