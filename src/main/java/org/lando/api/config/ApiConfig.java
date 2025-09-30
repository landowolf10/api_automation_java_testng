package org.lando.api.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ApiConfig {
    private static Properties properties;

    // Ruta del archivo de configuración
    private static final String CONFIG_PATH = "src/test/resources/application.properties";

    // Variables de entorno
    public static String BASE_URL;
    public static int TIMEOUT;
    public static String AUTH_TOKEN;
    public static String ENVIRONMENT;

    static {
        loadConfig();
    }

    /**
     * Loads the config from application.properties
     */
    private static void loadConfig() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
            BASE_URL = properties.getProperty("base.url", "https://restful-booker.herokuapp.com");
            TIMEOUT = Integer.parseInt(properties.getProperty("timeout", "5000"));
            //AUTH_TOKEN = properties.getProperty("auth.token", "defaultToken");
            ENVIRONMENT = properties.getProperty("environment", "dev");
        } catch (IOException e) {
            System.err.println("Error while loading configuration: " + e.getMessage());
            throw new RuntimeException("The configuration file could not be loaded.");
        }
    }

    /**
     * Gets the value of a specific property.
     * @param key - Property key.
     * @return Property value.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets the value of a specific property with a default value
     * @param key - Property key.
     * @param defaultValue - Default value if doesn't exists.
     * @return Property value or the default value.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
