package com.jalizadeh.todocial.ui.manager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class SeleniumDriverManager {

    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static synchronized void initDriver(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();

        Map< String, Object > prefs = new HashMap< String, Object >();
        Map< String, Object > profile = new HashMap < String, Object > ();
        Map < String, Object > contentSettings = new HashMap < String, Object > ();
        // SET CHROME OPTIONS
        // 0 - Default, 1 - Allow, 2 - Block
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.setCapability("acceptInsecureCerts", true); // Allows WebDriver to accept insecure certificates
        chromeOptions.addArguments("--incognito","--disable-gpu", "--window-size=1920,1080", "--ignore-certificate-errors", "--disable-popup-blocking");

        //driver.manage().window().maximize();
        //driver.manage().timeouts().pageLoadTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        //driver.manage().timeouts().implicitlyWait(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);

        drivers.set(new ChromeDriver(chromeOptions));
    }

    public static synchronized WebDriver getDriverInThreadLocal(){
        return drivers.get();
    }




}
