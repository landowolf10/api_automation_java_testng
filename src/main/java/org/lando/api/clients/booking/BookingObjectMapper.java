package org.lando.api.clients.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lando.api.models.request.booking.BookingRequest;
import org.lando.api.utils.ReadJsonData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //bookingRequest.setFirstname("Luis Orlando");

            /*
             * We can also add dynamic keys to the json
             * if needed (apart from the keys that are
             * already in the json file)
             * i.e: bookingRequest.setExtraField("newKey", "newValue"); or bookingRequest.setExtraField("priority", 5);
             */
            //bookingRequest.setExtraField("newKey", "newValue");

            /*
             * Or if we need to add values to an existing
             * json object like bookingdates in this case
             * we need to get the value with the getter
             * i.e: bookingRequest.getBookingdates().setExtraField("timezone", "UTC");
             */
            //bookingRequest.getBookingdates().setExtraField("timezone", "UTC");

            /*
             * We can also create objects inside the object
             * i.e:
             * Map<String, Object> metadata = new HashMap<>();
             * metadata.put("timezone", "UTC");
             * metadata.put("duration", "365d");
             * bookingRequest.getBookingdates().setExtraField("metadata", metadata);
             */

            /*Map<String, Object> metadata = new HashMap<>();
            metadata.put("timezone", "UTC");
            metadata.put("duration", "365d");

            bookingRequest.getBookingdates().setExtraField("metadata", metadata);*/

            /*
             * We can also create lists
             * i.e:
             * List<String> holidays = Arrays.asList("2023-12-25", "2024-01-01");
             * bookingRequest.getBookingdates().setExtraField("holidays", holidays);
             */

            /*List<String> holidays = Arrays.asList("2023-12-25", "2024-01-01");
            bookingRequest.getBookingdates().setExtraField("holidays", holidays);*/

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
