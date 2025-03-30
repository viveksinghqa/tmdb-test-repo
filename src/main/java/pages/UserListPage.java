package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class UserListPage {
    private WebDriver driver;

    // Locators
    private By listCard = By.linkText("My Inception List");
    private By movieTitle = By.xpath("//h2[contains(text(), 'Inception')]");

    // Constructor
    public UserListPage() {
        this.driver = DriverManager.getDriver();
    }

    public void openListDetailPage(){
        driver.findElement(listCard).click();
    }
}
