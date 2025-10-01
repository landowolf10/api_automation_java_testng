package org.lando.api.models.request.booking;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class BookingRequest {
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

    // Add dynamic keys
    private Map<String, Object> extraFields = new HashMap<>();

    @JsonAnySetter
    public void setExtraField(String key, Object value) {
        extraFields.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getExtraFields() {
        return extraFields;
    }

    // Getters and setters
    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

    public int getTotalprice() {
        return totalPrice;
    }

    public void setTotalprice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isDepositpaid() {
        return depositPaid;
    }

    public void setDepositpaid(boolean depositPaid) {
        this.depositPaid = depositPaid;
    }

    public BookingDates getBookingdates() {
        return bookingDates;
    }

    public void setBookingdates(BookingDates bookingDates) {
        this.bookingDates = bookingDates;
    }

    public String getAdditionalneeds() {
        return additionalNeeds;
    }

    public void setAdditionalneeds(String additionalNeeds) {
        this.additionalNeeds = additionalNeeds;
    }
}

