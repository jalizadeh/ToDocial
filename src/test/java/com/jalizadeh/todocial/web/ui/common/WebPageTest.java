package com.jalizadeh.todocial.web.ui.common;

import com.jalizadeh.todocial.web.ui.manager.SeleniumDriverManager;
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
