package com.jalizadeh.todocial.ui.common;

import com.jalizadeh.todocial.ui.manager.SeleniumDriverManager;
import org.openqa.selenium.WebDriver;

public class WebPageTest {

    protected WebDriver driver;

    protected void setup(){
        SeleniumDriverManager.initDriver();
    }

    protected  void teardown(){
        SeleniumDriverManager.getDriverInThreadLocal().quit();
    }

}
