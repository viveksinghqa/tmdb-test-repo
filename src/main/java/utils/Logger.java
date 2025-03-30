package utils;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String LOG_FILE = "api_test.log";

    public static void log(String message) {
        System.out.println(message);
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
