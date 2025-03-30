package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class TokenApprovalPage {
    private WebDriver driver;

    // Locator for "Approve" button
    private By approveButton = By.xpath("//button[contains(text(), 'Approve')]");

    // Constructor
    public TokenApprovalPage() {
        this.driver = DriverManager.getDriver();
    }

    // Open the authentication page with the request token
    public void openTokenApprovalPage(String requestToken) {
        String authUrl = "https://www.themoviedb.org/authenticate/" + requestToken;
        driver.get(authUrl);
    }

    // Click "Approve" button
    public void approveRequestToken() {
        driver.findElement(approveButton).click();
    }

//    public void tearDown() {
//        if (driver != null) {
//            driver.close();
//        }
//    }
}
