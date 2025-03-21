package org.lando.api.booking;

import io.restassured.RestAssured;
import org.lando.config.RestAssuredClient;
import io.restassured.response.Response;
import org.lando.models.request.booking.BookingRequest;

import java.util.List;

public class BookingAPI extends RestAssuredClient {
    public int validStatusCode() {
        Response response = RestAssured.get("/booking");

        return response.getStatusCode();
    }

    public List<String> getBookingIds() {
        Response response = RestAssured.get("/booking");

        return response.path("$");
    }

    public Response getBookingByValidId(long bookingId) {
        return RestAssured.get("/booking/" + bookingId);
    }

    public Response createBooking(String postJsonFile) { //BookingRequest bookingRequest
        String bookingJson = BookingObjectMapper.bookingObjectMapper(postJsonFile);

        return RestAssured.given()
                .body(bookingJson) //bookingRequest
                .when()
                .post("/booking");
    }

    public Response updateBooking(String updateJsonFile, long bookingIdUpdate) {
        String bookingJson = BookingObjectMapper.bookingObjectMapper(updateJsonFile);

        return RestAssured.given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body(bookingJson)
                .when()
                .put("/booking/" + bookingIdUpdate);
    }

    public Response deleteBooking(long bookingId) {
        return RestAssured.given()
                .when()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .delete("/booking/" + bookingId);
    }

    public Response getBookingSchema(long bookingId) {

        return RestAssured.get("/booking/" + bookingId);
    }

    public Response getBookingCreationResponseSchema() {

        return RestAssured.get("/booking/");
    }

    public Response getMalformedEndpoint(long bookingId) {
        return RestAssured.get("/booki" + bookingId);
    }
}
