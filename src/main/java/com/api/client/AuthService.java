package com.api.client;

import utils.ConfigManager;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    public static String generateRequestToken() throws Exception {
        String url = "https://api.themoviedb.org/3/authentication/token/new";

        // Headers with Authorization Token
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("accept", "application/json");

        ApiResponse response = ApiClient.sendRequest(url, "GET", null, headers);
        return response.getBody().get("request_token").asText();
    }

    public static void generateSessionId() throws Exception {
        String url = "https://api.themoviedb.org/3/authentication/session/new";

        // Headers with Authorization Token
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("accept", "application/json");
        headers.put("content-type", "application/json");

        String body = "{ \"request_token\": \"" + ConfigManager.REQUEST_TOKEN + "\" }";

        ApiResponse response = ApiClient.sendRequest(url, "POST", body, headers);
        ConfigManager.SESSION_ID = response.getBody().get("session_id").asText();
    }
}
