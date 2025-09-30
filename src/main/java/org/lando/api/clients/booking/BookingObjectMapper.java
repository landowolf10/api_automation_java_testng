package org.lando.api.clients.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lando.api.models.request.booking.BookingRequest;
import org.lando.api.utils.ReadJsonData;

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
             * i.e: bookingRequest.setFirstname("John");
             */
            //bookingRequest.setFirstname("Doe");

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
