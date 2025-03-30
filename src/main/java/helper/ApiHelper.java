package helper;

import com.api.client.ApiClient;
import com.api.client.ApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import utils.ConfigManager;

import java.util.HashMap;
import java.util.Map;

public class ApiHelper {

    public static ApiResponse searchMovie(String movieName) throws Exception {
        String endpoint = "/search/movie?query=" + movieName + "&include_adult=false&language=en-US&page=1";

        // Headers with Authorization Token
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("accept", "application/json");

        return ApiClient.sendRequest(ConfigManager.getBaseApiUrl() + endpoint, "GET", null, headers);
    }

    public static String fetchMovieNameFromSearchMovieResponse(ApiResponse response) {
        String movieName = null;
        if(!response.getBody().get("results").isArray()){
            throw new RuntimeException("Results are not an array");
        }
        for (JsonNode movie : response.getBody().get("results")) {
            if (movie.get("title").asText().equalsIgnoreCase("Inception")) {
                movieName = movie.get("title").asText();
                ConfigManager.MOVIE_ID = movie.get("id").asText();
                break;
            }
        }
        return movieName;
    }

    public static ApiResponse getMovieDetails(String movieId) throws Exception {
        String endpoint = "/movie/" + movieId + "?language=en-US";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("accept", "application/json");

        return ApiClient.sendRequest(ConfigManager.getBaseApiUrl() + endpoint, "GET", null, headers);
    }


    public static ApiResponse createNewList(String sessionId) throws Exception {
        String createListEndpoint = "/list?session_id=" + ConfigManager.SESSION_ID;
        String requestBody = "{ \"name\": \"My Inception List\", \"description\": \"List with Inception movie\", \"language\": \"en\" }";

        // Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("Content-Type", "application/json");

        return ApiClient.sendRequest(ConfigManager.getBaseApiUrl() + createListEndpoint, "POST", requestBody, headers);
    }

    public static ApiResponse addMovieToList(String listId, String movieId) throws Exception {
        String addMovieEndpoint = "/list/" + listId + "/add_item?session_id=" + ConfigManager.SESSION_ID;
        String addMovieRequestBody = "{ \"media_id\": " + movieId + " }";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("Content-Type", "application/json");
        headers.put("accept", "application/json");

        return ApiClient.sendRequest(ConfigManager.getBaseApiUrl() + addMovieEndpoint, "POST", addMovieRequestBody, headers);
    }

    public static ApiResponse getListDetails(String listId) throws Exception {
        String listDetailsEndpoint = "/list/" + listId;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("Content-Type", "application/json");

        return ApiClient.sendRequest(ConfigManager.getBaseApiUrl() + listDetailsEndpoint, "GET", null, headers);
    }

}
