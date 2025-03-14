package org.lando.models.request.booking;

public class SetBookingData {
    public static BookingRequest setBookingData() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2018-01-01");
        bookingDates.setCheckout("2019-01-01");

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setFirstname("Jim");
        bookingRequest.setLastname("Brown");
        bookingRequest.setTotalprice(111);
        bookingRequest.setDepositpaid(true);
        bookingRequest.setBookingdates(bookingDates);
        bookingRequest.setAdditionalneeds("Breakfast");

        return bookingRequest;
    }
}
