package com.lando.tests.booking;

import com.lando.tests.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.lando.api.clients.booking.BookingClient;
import org.lando.api.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.lando.api.exceptions.ApiException;
import org.lando.api.services.BookingService;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Listeners(TestListener.class)
public class BookingTest {
    BookingClient bookingClient = new BookingClient();
    BookingService bookingService = new BookingService(bookingClient);
    RestAssuredConfig restAssuredConfig = new RestAssuredConfig();

    @BeforeSuite
    public void setUp() {
        restAssuredConfig.setupRestAssured();
    }

    /*********************************************Happy paths*********************************************/
    @Test(description = "Validates that the response code of the API is 200")
    public void validateStatusCode() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Validates that the response code of the API is 200");
        });

        Assert.assertEquals(bookingService.validateStatusCode("/booking"), 200);
    }

    @Test(description = "Validates that API is responding with Id's")
    @Severity(SeverityLevel.MINOR)
    public void validateBookingIds() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Validates that API is responding with Id's");
        });
        Assert.assertFalse(bookingService.getBookingIds().isEmpty(), "No available booking Ids");
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
        Allure.step("Prepare test data");
        final String testDataPath = "booking/postBooking.json";

        try {
            HashMap<String, Object> createBookingObj = bookingService.createValidBooking("booking/postBooking");
            Response getResponse = (Response) createBookingObj.get("getResponse");
            JsonNode jsonNode = (JsonNode) createBookingObj.get("jsonNode");
            long bookingId = (long) createBookingObj.get("bookingId");


            Allure.getLifecycle().updateTestCase(testResult -> {
                testResult.setName("Validates that the API is creating a booking");
            });

            Assert.assertEquals(getResponse.statusCode(), 200);
            Assert.assertNotNull(getResponse.getBody());
            Assert.assertTrue(bookingId > 0);

            Assert.assertEquals(getResponse.jsonPath().getString("firstname"), jsonNode.get("firstname").asText());
            Assert.assertEquals(getResponse.jsonPath().getString("lastname"), jsonNode.get("lastname").asText());
            Assert.assertEquals(getResponse.jsonPath().getInt("totalprice"), jsonNode.get("totalprice").asInt());
            Assert.assertTrue(getResponse.jsonPath().getBoolean("depositpaid"));
            Assert.assertEquals(getResponse.jsonPath().getString("additionalneeds"), jsonNode.get("additionalneeds").asText());

            bookingService.getBookingCreationResponseSchema()
                    .then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("json_schemas/postPetResponseSchema.json"));
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
    public void validateBookingById() {
        HashMap<String, Object> validateBookingById = bookingService.validateBookingById("booking/postBooking");
        Response getResponse = (Response) validateBookingById.get("getResponse");
        long bookingId = (long) validateBookingById.get("bookingId");

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertNotNull(getResponse.getBody());

        bookingService.getBookingSchema(bookingId)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json_schemas/getBookingSchema.json"));
    }

    //Update existing booking
    @Test(description = "Validates that a given booking can be updated")
    public void validateUpdatedBooking() {
        HashMap<String, Object> updateBookingObj = bookingService.validateUpdatedBooking("booking/postBooking");
        Response getResponse = (Response) updateBookingObj.get("getResponse");
        Response updateBookingResponse = (Response) updateBookingObj.get("updateBookingResponse");
        JsonNode jsonNode = (JsonNode) updateBookingObj.get("jsonNode");

        Assert.assertEquals(updateBookingResponse.getStatusCode(), 200);
        Assert.assertNotNull(updateBookingResponse.getBody());

        Assert.assertEquals(getResponse.jsonPath().getString("firstname"), jsonNode.get("firstname").asText());
        Assert.assertEquals(getResponse.jsonPath().getString("lastname"), jsonNode.get("lastname").asText());
        Assert.assertEquals(getResponse.jsonPath().getInt("totalprice"), jsonNode.get("totalprice").asInt());
        Assert.assertTrue(getResponse.jsonPath().getBoolean("depositpaid"));
        Assert.assertEquals(getResponse.jsonPath().getString("additionalneeds"), jsonNode.get("additionalneeds").asText());

        bookingService.getBookingCreationResponseSchema()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("json_schemas/updateBookingResponseSchema.json"));
    }

    //Delete an existing booking
    @Test(description = "Validates that a booking can be deleted")
    public void validateDeletedBooking() {
        HashMap<String, Object> deleteBookingObj = bookingService.validateDeletedBooking("booking/postBooking");
        Response deleteBookingResponse = (Response) deleteBookingObj.get("deleteBookingResponse");
        Response getBookingResponse = (Response) deleteBookingObj.get("getBookingResponse");


        Assert.assertEquals(deleteBookingResponse.getStatusCode(), 201);
        Assert.assertNotNull(deleteBookingResponse.getBody());
        Assert.assertEquals(deleteBookingResponse.getBody().asString(), "Created");
        Assert.assertEquals(getBookingResponse.getBody().asString(), "Not Found");
    }

    /*********************************************Edge cases*********************************************/
    //Get error when trying to get a bookin by id with an unexisting id
    @Test(description = "Validates the response when trying to get a booking by invalid id")
    public void getBookingByInvalidId() {
        Response getInvalidId = bookingService.getBookingByInvalidId(1234567890);
        Assert.assertEquals(getInvalidId.getBody().asString(), "Not Found");
    }

    //Get error when trying to get a booking by status with a wrong/malformed endpoint
    @Test(description = "Validates the response when trying to get a booking with a malformed endpoint")
    public void getMalformedEndpoint() {
        Response malformedEndpoint = bookingService.getMalformedEndpoint();
        Assert.assertEquals(malformedEndpoint.getBody().asString(), "Not Found");
    }

    //Get error when trying to add a pet with a wrong/malformed endpoint

    //Get error when trying to add a pet with an empty body

    //Get error when trying to add a pet with a wrong/malformed body

    //Get error when trying to update an unexisting pet

    //Get error when trying to update a pet with an empty body

    //Get error when trying to update a pet with a wrong/malformed body

    //Get error when trying to delete an unexisting pet
}
