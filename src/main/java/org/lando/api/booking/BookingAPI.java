package org.lando.api.booking;

import org.lando.config.RestAssuredClient;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingAPI extends RestAssuredClient {
    public int validStatusCode() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booking");

        return response.getStatusCode();
    }

    public List<String> getBookingIds() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booking");

        return response.path("$");
    }

    public Response getBookingByValidId(long bookingId) {
        return given()
               .spec(requestSpec)
               .when()
               .get("/booking/" + bookingId);
    }

    public Response createBooking(String postJsonFile) { //BookingRequest bookingRequest
        String bookingJson = BookingObjectMapper.bookingObjectMapper(postJsonFile);

        return given()
               .spec(requestSpec)
               .body(bookingJson) //bookingRequest
               .when()
               .post("/booking");
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
