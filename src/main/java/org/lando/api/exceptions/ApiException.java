package org.lando.api.exceptions;

public class ApiException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;
    private final String endpoint;

    public ApiException(String message) {
        super(message);
        this.statusCode = -1;
        this.responseBody = null;
        this.endpoint = null;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
        this.responseBody = null;
        this.endpoint = null;
    }

    public ApiException(String message, int statusCode, String responseBody, String endpoint) {
        super(String.format("%s | Status: %d | Endpoint: %s", message, statusCode, endpoint));
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.endpoint = endpoint;
    }

    public ApiException(String message, int statusCode, String responseBody, String endpoint, Throwable cause) {
        super(String.format("%s | Status: %d | Endpoint: %s", message, statusCode, endpoint), cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.endpoint = endpoint;
    }

    // Getters
    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public String toString() {
        return String.format(
                "ApiException{message='%s', statusCode=%d, endpoint='%s', responseBody='%s'}",
                getMessage(),
                statusCode,
                endpoint,
                responseBody
        );
    }
}
