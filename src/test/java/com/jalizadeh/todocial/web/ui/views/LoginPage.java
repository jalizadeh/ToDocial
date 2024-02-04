package com.jalizadeh.todocial.web.ui.views;

import com.jalizadeh.todocial.web.ui.utils.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class LoginPage {

    private WebDriver driver;

    private final By usernameInput = By.xpath("//input[@id='username']");
    private final By passwordInput = By.xpath("//input[@id='password']");
    private final By loginButton = By.xpath("//button[@type='submit']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://localhost:8080/login");
    }

    public LoginPage checkIfElementsExist(){
        assertNotNull(SeleniumUtils.getElement(usernameInput));
        assertNotNull(SeleniumUtils.getElement(passwordInput));
        assertNotNull(SeleniumUtils.getElement(loginButton));
        return this;
    }

    public LoginPage fillFields(String username, String password){
        SeleniumUtils.writeText(usernameInput, username);
        SeleniumUtils.writeText(passwordInput, password);
        return this;
    }

    public LoginPage clickLoginButton(){
        SeleniumUtils.getElement(loginButton).click();
        return this;
    }
}
