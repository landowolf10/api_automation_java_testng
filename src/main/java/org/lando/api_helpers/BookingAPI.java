package org.lando.api_helpers;

import io.restassured.RestAssured;
import org.lando.config.RestAssuredClient;
import io.restassured.response.Response;
import org.lando.models.GetBookingResponse;
import org.lando.utils.ReadJsonData;


import java.math.BigInteger;
import java.util.HashMap;
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

    /*public List<String> getPetsByAvailableStatus(String status) {
        Response response = get("/pet/findByStatus?status=" + status);

        return response.jsonPath().getList("status");
    }*/

    public Response getBookingByValidId(long bookingId) {
        return RestAssured.get("/booking/" + bookingId);
    }

    public Response createBooking(String postJsonFile) {
        return RestAssured.given()
                .body(ReadJsonData.readJsonFile(postJsonFile))
                .when()
                .post("/booking");
    }

    public Response updatePet(String putJsonFile, long bookingIdUpdate) {
        return RestAssured.given()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body(ReadJsonData.readJsonFile(putJsonFile))
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

    public Response getPetCreationResponseSchema() {

        return RestAssured.get("/booking/");
    }
}
