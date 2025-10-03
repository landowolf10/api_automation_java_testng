package com.lando.tests.booking;

import com.lando.tests.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.lando.api.clients.booking.BookingClient;
import io.restassured.response.Response;
import org.lando.api.exceptions.ApiException;
import org.lando.api.services.BookingService;
import org.lando.api.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.JsonNode;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Backend")
@Feature("Booking API")
@Listeners(TestListener.class)
public class BookingTest extends BaseTest {
    private final BookingService bookingService = new BookingService(new BookingClient());

    private static final String POST_PAYLOAD = "booking/postBooking";
    private static final String UPDATE_PAYLOAD = "booking/updateBooking";
    private static final String UPDATE_SCHEMA = "json_schemas/updateBookingResponseSchema.json";
    private static final String CREATE_SCHEMA = "json_schemas/postBookingResponseSchema.json";
    private static final String GET_SCHEMA = "json_schemas/getBookingSchema.json";

    /*********************************************Happy paths*********************************************/
    @Test(description = "Validates that the response code of the API is 200")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Orlando")
    @Story("LM-12120-Healthcheck")
    public void validateStatusCode() {
        int status = bookingService.validateStatusCode("/booking");
        Assert.assertEquals(status, 200, "Expected status 200 but got " + status);
    }

    @Test(description = "Validates that API is responding with Id's")
    @Severity(SeverityLevel.MINOR)
    @Owner("Orlando")
    @Story("LM-12121-Get Bookings")
    public void validateBookingIds() {
        Response response = bookingService.getBookingIds();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertFalse(response.getBody().asString().isEmpty(), "No available booking Ids");
    }

    //Add new booking
    @Test(description = "Validates that the API is creating a booking")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST API endpoint to create a booking")
    @Owner("Orlando")
    @Tag("Booking")
    @Epic("Backend")
    @Feature("Create booking API")
    @Story("LM-12124-Create booking")
    public void validateCreatedBooking() {
        final String payload = "booking/postBooking";

        try {
            BookingService.BookingResult result = bookingService.createValidBooking(payload);

            Response getResponse = result.response();
            JsonNode jsonNode = result.payload();
            long bookingId = result.bookingId();

            Assert.assertEquals(getResponse.statusCode(), 200);
            Assert.assertNotNull(getResponse.getBody());
            Assert.assertTrue(bookingId > 0);

            // Field validations
            Assert.assertEquals(getResponse.jsonPath().getString("firstname"), jsonNode.get("firstname").asText());
            Assert.assertEquals(getResponse.jsonPath().getString("lastname"), jsonNode.get("lastname").asText());
            Assert.assertEquals(getResponse.jsonPath().getInt("totalprice"), jsonNode.get("totalprice").asInt());
            Assert.assertTrue(getResponse.jsonPath().getBoolean("depositpaid"));
            Assert.assertEquals(getResponse.jsonPath().getString("additionalneeds"), jsonNode.get("additionalneeds").asText());

            // Schema validation
            bookingService.getBookingCreationResponseSchema()
                    .then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath(CREATE_SCHEMA));
        } catch (ApiException e) {
            Allure.addAttachment("Error Details", "text/plain",
                    "Status: " + e.getStatusCode() + "\n" +
                            "Endpoint: " + e.getEndpoint() + "\n" +
                            "Response: " + e.getResponseBody());

            Assert.fail("Failed to create booking: " + e.getMessage());
        }
    }

    //Validate booking by id
    @Test(description = "Gets a booking by id")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Orlando")
    @Story("LM-12125-Get Booking by Id")
    public void validateBookingById() {
        BookingService.BookingResult result = bookingService.validateBookingById(POST_PAYLOAD);
        Response getResponse = result.response();
        long bookingId = result.bookingId();

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertNotNull(getResponse.getBody());

        bookingService.getBookingSchema(bookingId)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(GET_SCHEMA));
    }

    //Update existing booking
    @Test(description = "Validates that a given booking can be updated")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Orlando")
    @Story("LM-12126-Update Booking")
    public void validateUpdatedBooking() {
        BookingService.BookingResult result = bookingService.validateUpdatedBooking(UPDATE_PAYLOAD);
        Response updateResponse = result.response();
        JsonNode jsonNode = result.payload();

        Assert.assertEquals(updateResponse.getStatusCode(), 200);
        Assert.assertNotNull(updateResponse.getBody());

        Assert.assertEquals(updateResponse.jsonPath().getString("firstname"), jsonNode.get("firstname").asText());
        Assert.assertEquals(updateResponse.jsonPath().getString("lastname"), jsonNode.get("lastname").asText());
        Assert.assertEquals(updateResponse.jsonPath().getInt("totalprice"), jsonNode.get("totalprice").asInt());
        Assert.assertTrue(updateResponse.jsonPath().getBoolean("depositpaid"));
        Assert.assertEquals(updateResponse.jsonPath().getString("additionalneeds"), jsonNode.get("additionalneeds")
                .asText());


        bookingService.getBookingCreationResponseSchema()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(UPDATE_SCHEMA));
    }

    //Delete an existing booking
    @Test(description = "Validates that a booking can be deleted")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Orlando")
    @Story("LM-12127-Delete Booking")
    public void validateDeletedBooking() {
        Response deleteResponse = bookingService.validateDeletedBooking(POST_PAYLOAD);
        Assert.assertEquals(deleteResponse.getStatusCode(), 201);
        Assert.assertEquals(deleteResponse.getBody().asString(), "Created");
    }

    /*********************************************Edge cases*********************************************/
    //Get error when trying to get a booking by id with an unexisting id
    @Test(description = "Validates the response when trying to get a booking by invalid id")
    @Severity(SeverityLevel.MINOR)
    public void getBookingByIdRaw() {
        Response invalidResponse = bookingService.getBookingByIdRaw(9999999);
        Assert.assertEquals(invalidResponse.getStatusCode(), 404);
        Assert.assertEquals(invalidResponse.getBody().asString(), "Not Found");
    }

    //Get error when trying to get a booking by status with a wrong/malformed endpoint
    @Test(description = "Validates the response when trying to get a booking with a malformed endpoint")
    @Severity(SeverityLevel.MINOR)
    public void getMalformedEndpoint() {
        Response malformedResponse = bookingService.getMalformedEndpoint(POST_PAYLOAD);
        Assert.assertEquals(malformedResponse.getStatusCode(), 404);
        Assert.assertEquals(malformedResponse.getBody().asString(), "Not Found");
    }
}
