package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class UserHomePage {
    private WebDriver driver;

    // Locators
    private By profileIcon = By.xpath("//span[@class = 'avatar background_color pink']");
    private By listLink = By.xpath("//div[@class='k-child-animation-container']//a[@href='/u/vivek.singh14/lists']");

    // Constructor
    public UserHomePage() {
        this.driver = DriverManager.getDriver();
    }

    public void expandProfileMenuList(){
        driver.findElement(profileIcon).click();
    }

    public void openUserList(){
        driver.findElement(listLink).click();
    }
}
