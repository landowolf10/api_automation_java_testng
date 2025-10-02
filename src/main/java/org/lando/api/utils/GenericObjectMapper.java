package org.lando.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lando.api.models.request.booking.BookingRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericObjectMapper {
    /**
     * Converts a JSON to an object of any class and returns a formatted JSON.
     *
     * @param <T>        Class type
     * @param jsonFile   json file path
     * @param modelClass Class to map
     * @return           JSON as String
     */
    public static <T> String mapJsonToObject(String jsonFile, Class<T> modelClass) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            T object = objectMapper.readValue(ReadJsonData.readJsonFile(jsonFile), modelClass);

            /*
             * Here we can set values to the object if we
             * need/want to change some values
             * if (object instanceof BookingRequest) {
             *     ((BookingRequest)object).setFirstname("John");
             * }
             */
            if (object instanceof BookingRequest) {
                ((BookingRequest)object).setFirstname("John");
            }

            /*
             * We can also add dynamic keys to the json
             * if needed (apart from the keys that are
             * already in the json file)
             * i.e: ((BookingRequest)object).setExtraField("newKey", "newValue"); or ((BookingRequest)object)
             * .setExtraField("priority", 5);
             */
            /*if (object instanceof BookingRequest) {
                ((BookingRequest)object).setExtraField("newKey", "newValue");
            }*/

            /*
             * Or if we need to add values to an existing
             * json object like bookingdates in this case
             * we need to get the value with the getter
             * i.e: ((BookingRequest)object).getBookingdates().setExtraField("timezone", "UTC");
             */
            /*if (object instanceof BookingRequest) {
                ((BookingRequest)object).getBookingdates().setExtraField("timezone", "UTC");
            }*/

            /*
             * We can also create objects inside the object
             * i.e:
             * Map<String, Object> metadata = new HashMap<>();
             * metadata.put("timezone", "UTC");
             * metadata.put("duration", "365d");
             * ((BookingRequest)object).getBookingdates().setExtraField("metadata", metadata);
             */
            if (object instanceof BookingRequest) {
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("timezone", "UTC");
                metadata.put("duration", "365d");

                ((BookingRequest)object).getBookingdates().setExtraField("metadata", metadata);
            }

            /*
             * We can also create lists
             * i.e:
             * List<String> holidays = Arrays.asList("2023-12-25", "2024-01-01");
             * ((BookingRequest)object).getBookingdates().setExtraField("holidays", holidays);
             */
            /*if (object instanceof BookingRequest) {
                List<String> holidays = Arrays.asList("2023-12-25", "2024-01-01");
                ((BookingRequest)object).getBookingdates().setExtraField("holidays", holidays);
            }*/

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
