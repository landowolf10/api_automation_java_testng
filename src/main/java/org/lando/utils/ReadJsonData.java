package org.lando.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class ReadJsonData {
    static String baseJsonPath = "./src/test/resources/payloads/";

    public static InputStream readJsonFile(String jsonFile) {
        try {
            return new FileInputStream(baseJsonPath + jsonFile + ".json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode getJsonNode(String jsonFile) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readTree(new File(baseJsonPath + jsonFile + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
