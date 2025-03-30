package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigManager;
import utils.DriverManager;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//input[@value = \"Login\"]");

    // Constructor
    public LoginPage() {
        this.driver = DriverManager.getDriver();
    }

    // Open login page
    public void openLoginPage() {
        driver.get(ConfigManager.getBaseUiUrl() + "/login");
    }

    // Perform login
    public void login(String username, String password) throws InterruptedException {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        Thread.sleep(5000);
        driver.findElement(loginButton).click();
    }
}
