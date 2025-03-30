package helper;

import com.api.client.AuthService;
import pages.LoginPage;
import pages.TokenApprovalPage;
import pages.UserHomePage;
import utils.ConfigManager;

public class UiHelper {

    public static void performLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage();
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        System.out.println("✅ Successfully Logged In!");
    }

    public static void generateAndApproveRequestToken() throws Exception {
        String requestToken = AuthService.generateRequestToken();
        ConfigManager.REQUEST_TOKEN = requestToken;
        TokenApprovalPage tokenApprovalPage = new TokenApprovalPage();
        tokenApprovalPage.openTokenApprovalPage(requestToken);
        tokenApprovalPage.approveRequestToken();
        //tokenApprovalPage.tearDown();

        System.out.println("✅ Request Token Approved Successfully!");
    }

    public static void navigateToListPage() throws InterruptedException {

        UserHomePage userHomePage = new UserHomePage();
        userHomePage.expandProfileMenuList();
        Thread.sleep(3000);

        userHomePage.openUserList();
        Thread.sleep(3000);
    }

}
