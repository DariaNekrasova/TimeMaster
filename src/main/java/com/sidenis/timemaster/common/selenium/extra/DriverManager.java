package com.sidenis.timemaster.common.selenium.extra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static final String PATH_TO_FF_BINARY = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe";
    private static final String PATH_TO_CHROME_BINARY = "src/main/resources/chromedriver.exe";
    private static final String PATH_TO_GECKO = "src/main/resources/geckodriver.exe";
    private static final int IMPLICITY_WAIT = 5;
    private static final int PAGE_LOAD_TIMEOUT = 15;
    private static final int SCRIPT_TIMEOUT = 15;
    private static WebDriver driver;

    public static WebDriver createDriver() {
        driver = null;
        return createDriver(ConstantsUI.BROWSER);
    }

    public static boolean isElementPresent(WebElement element) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException nse) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            System.out.println("Creating driver...");
            driver = createDriver(ConstantsUI.BROWSER);
        }
        return driver;
    }

    private static WebDriver createDriver(String browser) {
        switch (browser) {
//            case "FF_GECKO":
//                System.setProperty("webdriver.gecko.driver", PATH_TO_GECKO);
//                FirefoxOptions options = new FirefoxOptions().
//                        setBinary(PATH_TO_FF_BINARY).
//                        addArguments("-console").
//                        addPreference("browser.cache.disk.enable", false);
//                driver = new FirefoxDriver(options);
//                break;
            case "FF_OLD":
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("browser.cache.disk.enable", false);
                caps.setCapability("acceptSslCerts", true);
                caps.setCapability("firefox_binary", PATH_TO_FF_BINARY);
                driver = new FirefoxDriver(caps);
                break;
            case "CHROME":
                System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_BINARY);
                driver = new ChromeDriver(DesiredCapabilities.chrome());
                break;
            default:
                throw new IllegalStateException("Unknown browser type specified");
        }
        setTimeouts(driver);
        driver.manage().window().maximize();
        return driver;
    }

    private static void setTimeouts(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(IMPLICITY_WAIT, TimeUnit.SECONDS);
        //  driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS);
    }

    public static void waitFor(ExpectedCondition con)
    {
        WebDriverWait wait = new WebDriverWait(driver, 105);
        wait.until(con);
    }

    public static class PageObject {
        protected WebDriver driver;

        public PageObject (WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);


        }
    }
}
