package org.lando.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lando.api.models.request.booking.BookingDates;

public class BookingResponse {
    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("totalprice")
    private int totalPrice;

    @JsonProperty("depositpaid")
    private boolean depositPaid;

    @JsonProperty("bookingdates")
    private BookingDates bookingDates;

    @JsonProperty("additionalneeds")
    private String additionalNeeds;

    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public int getTotalprice() {
        return totalPrice;
    }

    public boolean isDepositpaid() {
        return depositPaid;
    }

    public BookingDates getBookingdates() {
        return bookingDates;
    }

    public String getAdditionalneeds() {
        return additionalNeeds;
    }
}

