package org.lando.api.clients.booking;

import org.lando.api.clients.BaseClient;
import io.restassured.response.Response;
import org.lando.api.exceptions.ApiException;
import org.lando.api.models.request.booking.BookingRequest;
import org.lando.api.utils.GenericObjectMapper;

import java.util.List;

public class BookingClient extends BaseClient {
    private static final String BASE_ENDPOINT = "/booking";

    public int validStatusCode(String endpoint) {
        return get(endpoint).getStatusCode();
    }

    public Response getBookingIds() {
        return get(BASE_ENDPOINT);
    }

    public Response getBookingByValidId(long bookingId) {
        return get(BASE_ENDPOINT + "/" + bookingId);
    }

    public Response createBooking(String postJsonFile) {
        String bookingJson = GenericObjectMapper.mapJsonToObject(postJsonFile, BookingRequest.class);

        return post(BASE_ENDPOINT, bookingJson);
    }

    public Response updateBooking(String updateJsonFile, long bookingIdUpdate, String authHeader) {
        String bookingJson = GenericObjectMapper.mapJsonToObject(updateJsonFile, BookingRequest.class);

        return put(BASE_ENDPOINT + "/" + bookingIdUpdate, bookingJson, authHeader);
    }

    public Response deleteBooking(long bookingId, String authHeader) {
        return delete(BASE_ENDPOINT + "/" + bookingId, authHeader);
    }

    public Response getBookingSchema(long bookingId) {
        return get(BASE_ENDPOINT + "/" + bookingId);
    }

    public Response getBookingCreationResponseSchema() {
        return get(BASE_ENDPOINT);
    }

    public Response getBookingByIdRaw(long bookingId) {
        return requestSpec().get(BASE_ENDPOINT + "/" + bookingId);
    }

    public Response getMalformedEndpointRaw(long bookingId) {
        return requestSpec().get("/booki" + bookingId);
    }
}
