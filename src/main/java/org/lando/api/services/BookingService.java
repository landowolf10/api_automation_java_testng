package org.lando.api.services;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.lando.api.clients.booking.BookingClient;
import org.lando.api.utils.ReadJsonData;

import java.util.HashMap;
import java.util.List;

public class BookingService {
    public record BookingResult(long bookingId, Response response, JsonNode payload) {}
    private final BookingClient bookingClient;
    private static final String DEFAULT_AUTH_HEADER = "Basic YWRtaW46cGFzc3dvcmQxMjM=";

    public BookingService(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    /** Healthcheck / status validation **/
    public int validateStatusCode(String endpoint) {
        return bookingClient.validStatusCode(endpoint);
    }

    /** Get all booking ids **/
    public Response getBookingIds() {
        return bookingClient.getBookingIds();
    }

    /** Create booking and return full result **/
    public BookingResult createValidBooking(String payloadPath) {
        Response createResponse  = bookingClient.createBooking(payloadPath);
        long bookingId = createResponse .jsonPath().getLong("bookingid");
        Response getResponse = bookingClient.getBookingByValidId(bookingId);
        JsonNode jsonNode = ReadJsonData.getJsonNode(payloadPath);

        return new BookingResult(bookingId, getResponse, jsonNode);

    }

    /** Validate booking retrieval by id **/
    public BookingResult validateBookingById(String payloadPath) {
        Response createBookingResponse = bookingClient.createBooking(payloadPath);
        long bookingId = createBookingResponse.jsonPath().getLong("bookingid");
        Response getResponse = bookingClient.getBookingByValidId(bookingId);
        JsonNode jsonNode = ReadJsonData.getJsonNode(payloadPath);

        System.out.println("Response: " + getResponse.getBody().asString());

        return new BookingResult(bookingId, getResponse, jsonNode);
    }

    /** Update an existing booking **/
    public BookingResult validateUpdatedBooking(String payloadPath) {
        Response createBookingResponse = bookingClient.createBooking(payloadPath);
        long bookingId = createBookingResponse.jsonPath().getLong("bookingid");

        Response updateBookingResponse = bookingClient.updateBooking(payloadPath, bookingId, DEFAULT_AUTH_HEADER);
        //Response getResponse = bookingClient.getBookingByValidId(bookingId);
        JsonNode jsonNode = ReadJsonData.getJsonNode(payloadPath);

        return new BookingResult(bookingId, updateBookingResponse, jsonNode);
    }


    /** Delete a booking **/
    public Response validateDeletedBooking(String payloadPath) {
        Response createResponse = bookingClient.createBooking(payloadPath);
        long bookingId = createResponse.jsonPath().getLong("bookingid");

        return bookingClient.deleteBooking(bookingId, DEFAULT_AUTH_HEADER);
    }

    /** Get booking with an invalid id **/
    public Response getBookingByIdRaw(long invalidBookingId) {
        return bookingClient.getBookingByIdRaw(invalidBookingId);
    }

    /** Call malformed endpoint **/
    public Response getMalformedEndpoint(String payloadPath) {
        Response createResponse = bookingClient.createBooking(payloadPath);
        long bookingId = createResponse.jsonPath().getLong("bookingid");
        return bookingClient.getMalformedEndpointRaw(bookingId);
    }

    /** Schema validations **/
    public Response getBookingCreationResponseSchema() {
        return bookingClient.getBookingCreationResponseSchema();
    }

    public Response getBookingSchema(long bookingId) {
        return bookingClient.getBookingSchema(bookingId);
    }
}
