package com.sidenis.timemaster.common.annotation;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.openqa.selenium.WebDriver;


import static com.sidenis.timemaster.common.selenium.extra.DriverManager.createDriver;
import static java.util.concurrent.TimeUnit.MINUTES;

public class BaseUITest {

    protected static WebDriver driver = null;

    @Rule
    public TestRule testTimeout = new DisableOnDebug(new Timeout(10, MINUTES));


    @BeforeClass
    public static void setup() {
        driver = createDriver();
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


}
