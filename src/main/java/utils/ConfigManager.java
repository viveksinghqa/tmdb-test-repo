package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    public static String SESSION_ID = null;
    public static String REQUEST_TOKEN = null;
    public static String MOVIE_ID = null;

    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseApiUrl() {
        return getProperty("base.api.url");
    }

    public static String getBaseUiUrl() {
        return getProperty("base.ui.url");
    }

    public static String getBearerToken() {
        return getProperty("bearer.token");
    }

    public static String getUsername() {
        return getProperty("username");
    }

    public static String getPassword() {
        return getProperty("password");
    }

}
