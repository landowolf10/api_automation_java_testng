package org.lando.models;

public class PostBookingResponse {
    private int statusCode;
    private long bookingId;
    private String responseBody;

    public PostBookingResponse(int statusCode, long bookingId, String responseBody) {
        this.statusCode = statusCode;
        this.bookingId = bookingId;
        this.responseBody = responseBody;
    }

    // Getters
    public int getStatusCode() {
        return statusCode;
    }

    public long getBookingId() {
        return bookingId;
    }

    public String getResponseBody() {
        return responseBody;
    }
}