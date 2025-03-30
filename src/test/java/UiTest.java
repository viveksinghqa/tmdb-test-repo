import com.api.client.ApiResponse;
import com.api.client.AuthService;
import helper.ApiHelper;
import helper.UiHelper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utils.ConfigManager;
import utils.Logger;

public class UiTest {

    private WebDriver driver;

    @Test(priority = 1)
    public void testLoginAndVerifyMovieInList() throws Exception {
        // Step 1: Searching Movie
        ApiResponse searchMovieResponse = ApiHelper.searchMovie("Inception");
        Logger.log("Search Movie Response: " + searchMovieResponse);

        Assert.assertEquals(searchMovieResponse.getStatusCode(), 200, "Response code is not 200");
        Assert.assertEquals(ApiHelper.fetchMovieNameFromSearchMovieResponse(searchMovieResponse), "Inception");

        //Step 2: Getting movie details
        ApiResponse movieDetailsResponse = ApiHelper.getMovieDetails(ConfigManager.MOVIE_ID );
        Logger.log("Movie Details Response: " + movieDetailsResponse);

        Assert.assertEquals(movieDetailsResponse.getBody().get("original_language").asText(), "en", "Movie language is not 'en'");
        Assert.assertEquals(movieDetailsResponse.getBody().get("title").asText(), "Inception", "Movie title is not 'Inception'");

        // Step 3:
        UiHelper.performLogin();
        UiHelper.generateAndApproveRequestToken();
        AuthService.generateSessionId();

        ApiResponse createListResponse = ApiHelper.createNewList(ConfigManager.SESSION_ID);
        Logger.log("Create List Response: " + createListResponse);

        Assert.assertNotNull(createListResponse.getBody().get("list_id"), "List creation failed");
        String listId = createListResponse.getBody().get("list_id").asText();

        ApiResponse addMovieResponse = ApiHelper.addMovieToList(listId, ConfigManager.MOVIE_ID);
        Logger.log("Add Movie Response: " + addMovieResponse);

        // Verify List Contains Only One Movie
        ApiResponse listDetailsResponse = ApiHelper.getListDetails(listId);
        Logger.log("List Details Response: " + listDetailsResponse);

        Assert.assertEquals(listDetailsResponse.getBody().get("items").size(), 1, "List does not contain exactly 1 movie");
        Assert.assertEquals(listDetailsResponse.getBody().get("items").get(0).get("title").asText(), "Inception", "Movie title mismatch");

        // Step 4: Open Created List
        UiHelper.navigateToListPage();
        Thread.sleep(2000);

        UiHelper.openListDetailPage();
        Thread.sleep(2000);

        System.out.println("UI Verification Passed: Inception is in the list!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
