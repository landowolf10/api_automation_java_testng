package org.lando.models;

import io.restassured.response.Response;

public class GetBookingResponse {
    private final int statusCode;
    private final Response response;
    private final String responseBody;

    public GetBookingResponse(int statusCode, Response response, String responseBody) {
        this.statusCode = statusCode;
        this.response = response;
        this.responseBody = responseBody;
    }

    // Getters
    public int getStatusCode() {
        return statusCode;
    }

    public Response getResponse() {
        return response;
    }

    public String getResponseBody() {
        return responseBody;
    }
}