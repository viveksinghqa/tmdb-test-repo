package com.api.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import reporting.ExtentTestNGListener;
import utils.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ApiClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ApiResponse sendRequest(String uri, String method, String body, Map<String, String> headers) throws Exception {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");

        // Add custom headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // Handle request body for POST, PUT requests
        if (method.equals("POST") || method.equals("PUT")) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = body.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }

        // Handle response codes
        int responseCode = conn.getResponseCode();
        String response = readResponse(conn, responseCode);

        // Log request and response
        Logger.log("Request: " + method + " " + uri);
        if (body != null) {
            Logger.log("Request Body: " + body);
        }
        Logger.log("Response Code: " + responseCode);
        Logger.log("Response: " + response);

        String requestDetails = "GET " + uri;
        String responseDetails = "Status Code: " + responseCode + "\nResponse:\n" + response.toString();

        ExtentTestNGListener.logApiDetails(requestDetails, responseDetails);

        // Convert response to JSON
        JsonNode jsonResponse = objectMapper.readTree(response);
        return new ApiResponse(responseCode, jsonResponse);
    }

    // Helper method to read response
    private static String readResponse(HttpURLConnection conn, int responseCode) throws IOException {
        InputStream is = (responseCode < 400) ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        return response.toString();
    }
}
