package org.lando.api.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.lando.api.clients.booking.BookingClient;
import org.lando.api.exceptions.ApiException;
import org.lando.api.models.request.booking.BookingRequest;
import org.lando.api.models.response.BookingResponse;
import org.lando.api.utils.ReadJsonData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {
    private final BookingClient bookingClient;

    public BookingService(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    public int validateStatusCode(String endpoint) {
        return bookingClient.validStatusCode(endpoint);
    }

    public List<String> getBookingIds() {
        return bookingClient.getBookingIds();
    }

    public HashMap<String, Object> createValidBooking(String payloadPath) {
        try {
            Response bookingResponse = bookingClient.createBooking(payloadPath);
            long bookingId = bookingResponse.jsonPath().getLong("bookingid");
            Response getResponse = bookingClient.getBookingByValidId(bookingId);

            HashMap<String, Object> result = new HashMap<>();
            result.put("bookingId", bookingId);
            result.put("getResponse", getResponse);
            result.put("jsonNode", ReadJsonData.getJsonNode(payloadPath));

            return result;
        } catch (ApiException e) {
            throw new ApiException(
                    "Failed to create booking: " + e.getMessage(),
                    e.getStatusCode(),
                    e.getResponseBody(),
                    e.getEndpoint()
            );
        }
    }

    public HashMap<String, Object> validateBookingById(String payloadPath) {
        HashMap<String, Object> obj = new HashMap<>();

        Response createBookingResponse = bookingClient.createBooking(payloadPath);
        long bookingId = createBookingResponse.jsonPath().getLong("bookingid");
        Response getResponse = bookingClient.getBookingByValidId(bookingId);

        System.out.println("Response: " + getResponse.getBody().asString());

        obj.put("getResponse", getResponse);
        obj.put("bookingId", bookingId);

        return obj;
    }

    public HashMap<String, Object> validateUpdatedBooking(String payloadPath) {
        HashMap<String, Object> obj = new HashMap<>();

        Response createBookingResponse = bookingClient.createBooking("booking/updateBooking");
        long bookingId = createBookingResponse.jsonPath().getLong("bookingid");
        Response updateBookingResponse = bookingClient.updateBooking("booking/updateBooking", bookingId);
        Response getResponse = bookingClient.getBookingByValidId(bookingId);
        JsonNode jsonNode = ReadJsonData.getJsonNode("booking/updateBooking");

        obj.put("updateBookingResponse", updateBookingResponse);
        obj.put("getResponse", getResponse);
        obj.put("jsonNode", jsonNode);
        obj.put("bookingId", bookingId);

        return obj;
    }


    public HashMap<String, Object> validateDeletedBooking(String payloadPath) {
        HashMap<String, Object> obj = new HashMap<>();

        Response createBooking = bookingClient.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response deleteBookingResponse = bookingClient.deleteBooking(bookingId);
        Response getBookingResponse = bookingClient.getBookingByValidId(bookingId);

        obj.put("deleteBookingResponse", deleteBookingResponse);
        obj.put("getBookingResponse", getBookingResponse);

        return obj;
    }

    public Response getBookingByInvalidId(long invalidBookingId) {
        Response getInvalidBookingResponse = bookingClient.getBookingByValidId(invalidBookingId);

        System.out.println("Invalid Id response: " + getInvalidBookingResponse.getBody().asString());

        return getInvalidBookingResponse;
    }

    public Response getMalformedEndpoint() {
        Response createBooking = bookingClient.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response getBookingResponse = bookingClient.getMalformedEndpoint(bookingId);

        System.out.println("Malformed endpoint response: " + getBookingResponse.getBody().asString());

        return getBookingResponse;
    }

    public Response getBookingCreationResponseSchema() {
        return bookingClient.getBookingCreationResponseSchema();
    }

    public Response getBookingSchema(long bookingId) {
        return bookingClient.getBookingSchema(bookingId);
    }
}
