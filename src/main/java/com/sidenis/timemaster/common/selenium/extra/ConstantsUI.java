package com.sidenis.timemaster.common.selenium.extra;

import java.io.IOException;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.sidenis.timemaster.common.selenium.extra.QALogger.qaLogger;

public class ConstantsUI {
    public static final String URL = "http://timemaster-dev3.sidenis.io/";
    public static final String URL_TimeTracker = "http://timemaster-dev3.sidenis.io/time-tracker";
    public static final String loginURL = "http://timemaster-dev3.sidenis.io/login";
    public static final String URL_Settings = "http://timemaster-dev3.sidenis.io/settings";


    private static final String PROPERTIES_FILE = "/application.properties";
    private static final String JENKINS_JOB_NAME;
    public static final String BASE_URI = "";
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String BROWSER;

    public static final String userName_AA = "alexey.avdeev";
    public static final String userPassword_AA = "123av";
    public static final int ID_AA = 163;


    static {
        JENKINS_JOB_NAME = System.getenv("JOB_NAME");
        USERNAME = getProperty("user.name");
        PASSWORD = getProperty("user.password");
        BROWSER = getProperty("browser.type");
    }

    private static String getProperty(String propertyName) {
        if (isNullOrEmpty(JENKINS_JOB_NAME)) {
            return loadProperty(propertyName);
        }
        return System.getProperty(propertyName);
    }

    private static String loadProperty(String propertyName) {
        Properties props = new Properties();
        try {
            props.load(ConstantsUI.class.getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            qaLogger.info("Can't find 'application.properties'. Please make sure it's available in src/resources");
            e.printStackTrace();
        }
        return props.getProperty(propertyName);
    }
}
