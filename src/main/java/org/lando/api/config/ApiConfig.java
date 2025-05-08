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
     * Carga la configuración desde application.properties
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
            System.err.println("Error al cargar la configuración: " + e.getMessage());
            throw new RuntimeException("No se pudo cargar el archivo de configuración.");
        }
    }

    /**
     * Obtiene el valor de una propiedad específica.
     * @param key - Clave de la propiedad.
     * @return Valor de la propiedad.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Obtiene el valor de una propiedad con un valor predeterminado.
     * @param key - Clave de la propiedad.
     * @param defaultValue - Valor predeterminado si no existe.
     * @return Valor de la propiedad o el valor predeterminado.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
