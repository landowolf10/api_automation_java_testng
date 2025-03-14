package org.lando.api.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lando.models.request.booking.BookingRequest;
import org.lando.utils.ReadJsonData;

public class BookingObjectMapper {
    public static String bookingObjectMapper(String postBookingFile) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //Read the JSON file and convert it to a BookingRequest object
            BookingRequest bookingRequest = objectMapper.readValue(ReadJsonData.readJsonFile(postBookingFile),
                    BookingRequest.class);

            /*
             * Here we can set values to the object if we
             * need/want to change some values
             * i.e: bookingRequest.setFirstname("Andy");
             */
            //bookingRequest.setFirstname("Wolf");

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
