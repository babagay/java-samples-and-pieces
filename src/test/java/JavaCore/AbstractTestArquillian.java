package JavaCore;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.testng.Arquillian;
import org.junit.runners.model.InitializationError;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

public class AbstractTestArquillian extends Arquillian
{
    @Drone
    protected WebDriver browser;

    @BeforeMethod
    public void loadPlatformPageBeforeTestMethod() {
//        browser.manage().deleteAllCookies();
//        String pageURL = "https://google.com";
//        browser.getInstance(pageURL);
    }
}
