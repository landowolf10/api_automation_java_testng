package org.lando.api.clients.booking;

import org.lando.api.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.lando.api.exceptions.ApiException;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingClient extends RestAssuredConfig {
    private void validateResponse(Response response, String operation, String endpoint) {
        if (response.statusCode() >= 400) {
            throw new ApiException(
                    "API Operation Failed: " + operation,
                    response.statusCode(),
                    response.getBody().asString(),
                    endpoint
            );
        }
    }

    public int validStatusCode(String endpoint) {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(endpoint);

        validateResponse(response, "GET status check", endpoint);
        return response.getStatusCode();
    }

    public List<String> getBookingIds() {
        String endpoint = "/booking";
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(endpoint);

        validateResponse(response, "Get Booking IDs", endpoint);
        return response.path("$");
    }

    public Response getBookingByValidId(long bookingId) {
        String endpoint = "/booking/" + bookingId;
        return given()
                .spec(requestSpec)
                .when()
                .get(endpoint);

        //validateResponse(response, "Get Booking by ID", endpoint);
    }

    public Response createBooking(String postJsonFile) {
        String endpoint = "/booking";
        String bookingJson = BookingObjectMapper.bookingObjectMapper(postJsonFile);

        Response response = given()
                .spec(requestSpec)
                .body(bookingJson)
                .when()
                .post(endpoint);

        validateResponse(response, "Create Booking", endpoint);
        return response;
    }

    public Response updateBooking(String updateJsonFile, long bookingIdUpdate) {
        String bookingJson = BookingObjectMapper.bookingObjectMapper(updateJsonFile);

        return given()
               .spec(requestSpec)
               .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
               .body(bookingJson)
               .when()
               .put("/booking/" + bookingIdUpdate);
    }

    public Response deleteBooking(long bookingId) {
        return given()
               .spec(requestSpec)
               .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
               .when()
               .delete("/booking/" + bookingId);
    }

    public Response getBookingSchema(long bookingId) {

        return given()
               .spec(requestSpec)
               .get("/booking/" + bookingId);
    }

    public Response getBookingCreationResponseSchema() {

        return given()
               .spec(requestSpec)
               .get("/booking/");
    }

    public Response getMalformedEndpoint(long bookingId) {
        return given()
               .spec(requestSpec)
               .get("/booki" + bookingId);
    }
}
