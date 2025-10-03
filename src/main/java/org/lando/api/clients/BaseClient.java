package org.lando.api.clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.lando.api.config.RestAssuredConfig;
import org.lando.api.exceptions.ApiException;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected RequestSpecification requestSpec() {
        return given()
               .spec(RestAssuredConfig
               .getRequestSpec());
    }

    protected Response get(String endpoint) {
        Response response = requestSpec()
                            .when()
                            .get(endpoint);
        logResponse("GET", endpoint, response);
        validateResponse(response, "GET", endpoint);
        return response;
    }

    protected Response post(String endpoint, Object body) {
        Response response = requestSpec()
                            .body(body)
                            .when()
                            .post(endpoint);
        logResponse("POST", endpoint, response);
        validateResponse(response, "POST", endpoint);
        return response;
    }

    protected Response put(String endpoint, Object body, String authHeader) {
        Response response = requestSpec()
                .header("Authorization", authHeader)
                .body(body)
                .when()
                .put(endpoint);

        logResponse("PUT", endpoint, response);
        validateResponse(response, "PUT", endpoint);
        return response;
    }

    protected Response delete(String endpoint, String authHeader) {
        Response response = requestSpec()
                .header("Authorization", authHeader)
                .when()
                .delete(endpoint);

        logResponse("DELETE", endpoint, response);
        validateResponse(response, "DELETE", endpoint);
        return response;
    }

    public void validateResponse(Response response, String operation, String endpoint) {
        if (response.statusCode() >= 400) {
            throw new ApiException(
                    String.format("API Operation Failed: %s %s", operation, endpoint),
                    response.statusCode(),
                    response.getBody().asString(),
                    endpoint
            );
        }
    }

    private void logResponse(String method, String endpoint, Response response) {
        System.out.printf("📌 %s %s%n", method, endpoint);
        System.out.printf("💬 Status: %d%n", response.statusCode());
        //System.out.printf("📄 Response: %s%n", response.getBody().asPrettyString());
    }
}