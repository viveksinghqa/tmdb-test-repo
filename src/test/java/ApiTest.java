import com.api.client.ApiClient;
import com.api.client.ApiResponse;
import com.api.client.AuthService;
import helper.ApiHelper;
import helper.UiHelper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utils.ConfigManager;
import utils.DriverManager;
import utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {

    private static String listId;

    @Test(priority = 1)
    public void testSearchMovieInception() throws Exception {

        ApiResponse response = ApiHelper.searchMovie("Inception");
        Logger.log("Search Movie Response: " + response);

        Assert.assertEquals(response.getStatusCode(), 200, "Response code is not 200");
        Assert.assertEquals(ApiHelper.fetchMovieNameFromSearchMovieResponse(response), "Inception");
        Assert.assertNotNull(ConfigManager.MOVIE_ID, "Movie 'Inception' not found in search results");
    }

    @Test(priority = 2, dependsOnMethods = "testSearchMovieInception")
    public void testGetMovieDetails() throws Exception {
        ApiResponse response = ApiHelper.getMovieDetails(ConfigManager.MOVIE_ID );
        Logger.log("Movie Details Response: " + response);

        Assert.assertEquals(response.getBody().get("original_language").asText(), "en", "Movie language is not 'en'");
        Assert.assertEquals(response.getBody().get("title").asText(), "Inception", "Movie title is not 'Inception'");
    }

    @Test(priority = 3, dependsOnMethods = "testGetMovieDetails")
    public void testCreateMovieListAndAddMovie() throws Exception {

        UiHelper.performLogin();
        UiHelper.generateAndApproveRequestToken();
        AuthService.generateSessionId();

        ApiResponse createListResponse = ApiHelper.createNewList(ConfigManager.SESSION_ID);
        Logger.log("Create List Response: " + createListResponse);

        Assert.assertNotNull(createListResponse.getBody().get("list_id"), "List creation failed");
        listId = createListResponse.getBody().get("list_id").asText();

        ApiResponse addMovieResponse = ApiHelper.addMovieToList(listId, ConfigManager.MOVIE_ID);
        Logger.log("Add Movie Response: " + addMovieResponse);

        // Step 3: Verify List Contains Only One Movie
        ApiResponse listDetailsResponse = ApiHelper.getListDetails(listId);
        Logger.log("List Details Response: " + listDetailsResponse);

        Assert.assertEquals(listDetailsResponse.getBody().get("items").size(), 1, "List does not contain exactly 1 movie");
        Assert.assertEquals(listDetailsResponse.getBody().get("items").get(0).get("title").asText(), "Inception", "Movie title mismatch");
    }

    @Test(priority = 4, dependsOnMethods = "testCreateMovieListAndAddMovie")
    public void testCleanupMovieList() throws Exception {
        String deleteListEndpoint = "/list/" + listId + "&session_id=" + ConfigManager.SESSION_ID;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ConfigManager.getBearerToken());
        headers.put("accept", "application/json");

        ApiResponse response = ApiClient.sendRequest(ConfigManager.getBaseApiUrl() + deleteListEndpoint, "DELETE", null, headers);
        Logger.log("Delete List Response: " + response);
    }

    @AfterClass
    public void tearDown() {
        DriverManager.closeDriver();
    }

}
