package com.lando.tests.booking;

import com.lando.tests.TestListener;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.lando.api.booking.BookingAPI;
import org.lando.config.RestAssuredClient;
import io.restassured.response.Response;
import org.lando.utils.ReadJsonData;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.JsonNode;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Listeners(TestListener.class)
public class BookingTest {
    BookingAPI bookingAPI = new BookingAPI();
    RestAssuredClient restAssuredClient = new RestAssuredClient();

    @BeforeTest
    public void setUp() {
        restAssuredClient.setupRestAssured();
    }

    /*********************************************Happy paths*********************************************/
    @Test(description = "Validates that the response code of the API is 200")
    public void validateStatusCode() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Validates that the response code of the API is 200");
        });

        Assert.assertEquals(bookingAPI.validStatusCode(), 200);
    }

    @Test(description = "Validates that API is responding with Id's")
    @Severity(SeverityLevel.MINOR)
    public void validateBookingIds() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Validates that API is responding with Id's");
        });
        Assert.assertFalse(bookingAPI.getBookingIds().isEmpty(), "No available booking Ids");
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
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Validates that the API is creating a booking");
        });

        //BookingRequest bookingRequest = SetBookingData.setBookingData();
        Response bookingResponse = bookingAPI.createBooking("booking/postBooking");
        long bookingId = bookingResponse.jsonPath().getLong("bookingid");
        Response response = bookingAPI.getBookingByValidId(bookingId);
        JsonNode jsonNode = ReadJsonData.getJsonNode("booking/postBooking");

        Assert.assertEquals(bookingResponse.getStatusCode(), 200);
        Assert.assertNotNull(bookingResponse.getBody());
        Assert.assertTrue(bookingId > 0);

        Assert.assertEquals(response.jsonPath().getString("firstname"), jsonNode.get("firstname").asText());
        Assert.assertEquals(response.jsonPath().getString("lastname"), jsonNode.get("lastname").asText());
        Assert.assertEquals(response.jsonPath().getInt("totalprice"), jsonNode.get("totalprice").asInt());
        Assert.assertTrue(response.jsonPath().getBoolean("depositpaid"));
        Assert.assertEquals(response.jsonPath().getString("additionalneeds"), jsonNode.get("additionalneeds").asText());

        bookingAPI.getBookingCreationResponseSchema()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("postPetResponseSchema.json"));
    }

    //Validate booking by id
    @Test(description = "Gets a booking by id")
    public void validateBookingById() {
        Response createBooking = bookingAPI.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response getBookingResponse = bookingAPI.getBookingByValidId(bookingId);

        System.out.println("Response: " + getBookingResponse.getBody().asString());

        Assert.assertEquals(getBookingResponse.getStatusCode(), 200);
        Assert.assertNotNull(getBookingResponse.getBody());

        bookingAPI.getBookingSchema(bookingId)
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("getBookingSchema.json"));
    }

    //Update existing booking
    @Test(description = "Validates that a given booking can be updated")
    public void validateUpdatedBooking() {
        Response createBooking = bookingAPI.createBooking("booking/updateBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response bookingResponse = bookingAPI.updateBooking("booking/updateBooking", bookingId);
        Response response = bookingAPI.getBookingByValidId(bookingId);
        JsonNode jsonNode = ReadJsonData.getJsonNode("booking/updateBooking");

        Assert.assertEquals(bookingResponse.getStatusCode(), 200);
        Assert.assertNotNull(bookingResponse.getBody());

        Assert.assertEquals(response.jsonPath().getString("firstname"), jsonNode.get("firstname").asText());
        Assert.assertEquals(response.jsonPath().getString("lastname"), jsonNode.get("lastname").asText());
        Assert.assertEquals(response.jsonPath().getInt("totalprice"), jsonNode.get("totalprice").asInt());
        Assert.assertTrue(response.jsonPath().getBoolean("depositpaid"));
        Assert.assertEquals(response.jsonPath().getString("additionalneeds"), jsonNode.get("additionalneeds").asText());

        bookingAPI.getBookingCreationResponseSchema()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("updateBookingResponseSchema.json"));
    }

    //Delete an existing booking
    @Test(description = "Validates that a booking can be deleted")
    public void validateDeletedBooking() {
        Response createBooking = bookingAPI.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response bookingResponse = bookingAPI.deleteBooking(bookingId);
        Response getBookingResponse = bookingAPI.getBookingByValidId(bookingId);


        Assert.assertEquals(bookingResponse.getStatusCode(), 201);
        Assert.assertNotNull(bookingResponse.getBody());
        Assert.assertEquals(bookingResponse.getBody().asString(), "Created");
        Assert.assertEquals(getBookingResponse.getBody().asString(), "Not Found");
    }

    /*********************************************Edge cases*********************************************/
    //Get error when trying to get a bookin by id with an unexisting id
    @Test(description = "Validates the response when trying to get a booking by invalid id")
    public void getBookingByInvalidId() {
        Response getBookingResponse = bookingAPI.getBookingByValidId(1234567890);

        System.out.println("Invalid Id response: " + getBookingResponse.getBody().asString());
        Assert.assertEquals(getBookingResponse.getBody().asString(), "Not Found");
    }

    //Get error when trying to get a booking by status with a wrong/malformed endpoint
    @Test(description = "Validates the response when trying to get a booking with a malformed endpoint")
    public void getMalformedEndpoint() {
        Response createBooking = bookingAPI.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response getBookingResponse = bookingAPI.getMalformedEndpoint(bookingId);

        System.out.println("Malformed endpoint response: " + getBookingResponse.getBody().asString());
        //Assert.assertEquals(getBookingResponse.getBody().asString(), "Not Found");
    }

    //Get error when trying to add a pet with a wrong/malformed endpoint

    //Get error when trying to add a pet with an empty body

    //Get error when trying to add a pet with a wrong/malformed body

    //Get error when trying to update an unexisting pet

    //Get error when trying to update a pet with an empty body

    //Get error when trying to update a pet with a wrong/malformed body

    //Get error when trying to delete an unexisting pet
}
