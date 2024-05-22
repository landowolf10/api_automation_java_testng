package com.lando.tests.booking;

import org.lando.api_helpers.BookingAPI;
import org.lando.config.RestAssuredClient;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.lando.models.GetBookingResponse;
import org.lando.models.PostBookingResponse;
import org.lando.utils.ReadJsonData;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingTest {
    BookingAPI bookingAPI = new BookingAPI();
    RestAssuredClient restAssuredClient = new RestAssuredClient();

    @BeforeTest
    public void setUp() {
        restAssuredClient.setupRestAssured();
    }

    /*********************************************Happy paths*********************************************/
    @Test
    public void validateStatusCode() {
        Assert.assertEquals(bookingAPI.validStatusCode(), 200);
    }

    @Test
    public void validateBookingIds() {
        Assert.assertFalse(bookingAPI.getBookingIds().isEmpty(), "No available booking Ids");
    }

    /*@Test
    public void validateAvailableStatus() {
        Assert.assertTrue(bookingHelper.getPetsByAvailableStatus("available").stream().allMatch(status -> status.equals(
                "available")), "Not all status values are 'available'");

    }*/

    //Add new booking
    @Test
    public void validateCreatedPet() {
        Response bookingResponse = bookingAPI.createBooking("booking/postBooking");
        long bookingId = bookingResponse.jsonPath().getLong("bookingid");
        Response response = bookingAPI.getBookingByValidId(bookingId);

        JsonNode jsonNode = ReadJsonData.getJsonNode("booking/postBooking");

        Assert.assertEquals(bookingResponse.getStatusCode(), 200);
        Assert.assertNotNull(bookingResponse.getBody());
        Assert.assertTrue(bookingId > 0);

        Assert.assertEquals(response.jsonPath().getString("firstname"), jsonNode.get("firstname")
                .asText());
        Assert.assertEquals(response.jsonPath().getString("lastname"), jsonNode.get("lastname")
                .asText());
        Assert.assertEquals(response.jsonPath().getString("totalprice"), jsonNode.get(
                "totalprice").asText());
        Assert.assertEquals(response.jsonPath().getString("depositpaid"), jsonNode.get(
                "depositpaid").asText());
        Assert.assertEquals(response.jsonPath().getString("additionalneeds"), jsonNode.get(
                "additionalneeds").asText());

        /*bookingAPI.getPetCreationResponseSchema()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("postPetResponseSchema.json"));*/
    }

    //Validate booking by id
    @Test
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
    @Test
    public void validateUpdatedBooking() {
        Response createBooking = bookingAPI.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response bookingResponse = bookingAPI.updatePet("booking/updateBooking", bookingId);
        Response response = bookingAPI.getBookingByValidId(bookingId);

        Assert.assertEquals(bookingResponse.getStatusCode(), 200);
        Assert.assertNotNull(bookingResponse.getBody());

        JsonNode jsonNode = ReadJsonData.getJsonNode("booking/updateBooking");

        Assert.assertEquals(response.jsonPath().getString("firstname"), jsonNode.get("firstname")
                .asText());
        Assert.assertEquals(response.jsonPath().getString("lastname"), jsonNode.get("lastname")
                .asText());
        Assert.assertEquals(response.jsonPath().getString("totalprice"), jsonNode.get(
                "totalprice").asText());
        Assert.assertEquals(response.jsonPath().getString("depositpaid"), jsonNode.get(
                "depositpaid").asText());
        Assert.assertEquals(response.jsonPath().getString("additionalneeds"), jsonNode.get(
                "additionalneeds").asText());

        /*bookingAPI.getPetCreationResponseSchema()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("updateBookingResponseSchema.json"));*/
    }

    //Delete a pet
    @Test
    public void validateDeletedBooking() {
        Response createBooking = bookingAPI.createBooking("booking/postBooking");
        long bookingId = createBooking.jsonPath().getLong("bookingid");
        Response bookingResponse = bookingAPI.deleteBooking(bookingId);

        Assert.assertEquals(bookingResponse.getStatusCode(), 201);
        Assert.assertNotNull(bookingResponse.getBody());
        Assert.assertEquals(bookingResponse.getBody().asString(), "Created");
    }

    /*********************************************Edge cases*********************************************/
    //Get error when trying to get a pet by status with an invalid status

    //Get empty body when trying to get an unexisting status

    //Get error when trying to get a pet by status with a wrong/malformed endpoint

    //Get error when trying to add a pet with a wrong/malformed endpoint

    //Get error when trying to add a pet with an empty body

    //Get error when trying to add a pet with a wrong/malformed body

    //Get error when trying to update an unexisting pet

    //Get error when trying to update a pet with an empty body

    //Get error when trying to update a pet with a wrong/malformed body

    //Get error when trying to delete an unexisting pet
}
