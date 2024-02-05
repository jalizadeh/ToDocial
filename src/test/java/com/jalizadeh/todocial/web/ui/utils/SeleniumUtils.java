package com.jalizadeh.todocial.web.ui.utils;

import com.jalizadeh.todocial.web.ui.manager.SeleniumDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class SeleniumUtils {

    private static final int TIMEOUT_WAIT = 20; // in seconds
    private static final int TIMEOUT_POLLING = 250; // in milliseconds


    public static void refreshPage(){
        SeleniumDriverManager.getDriverInThreadLocal().navigate().refresh();
    }

    public static void writeText(By by, String text) {
        WebElement element = getElement(by);
        element.clear();
        element.sendKeys(text);
    }

    public static WebElement getElement(By by) {
        return waitForElement(by);
    }

    public static WebElement waitForElement(By by) {
        FluentWait<WebDriver> fw = new FluentWait<>(SeleniumDriverManager.getDriverInThreadLocal())
                .withTimeout(Duration.ofSeconds(TIMEOUT_WAIT))
                .pollingEvery(Duration.ofMillis(TIMEOUT_POLLING))
                .ignoring(NoSuchElementException.class);
        return fw.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static boolean elementNotVisible(By element) {
        try {
            waitForNotVisibleElement(element);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static void waitForNotVisibleElement(By by) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(
                SeleniumDriverManager.getDriverInThreadLocal())
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

}
