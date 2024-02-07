package com.jalizadeh.todocial.ui.views;

import com.jalizadeh.todocial.ui.common.WebPage;
import com.jalizadeh.todocial.ui.utils.SeleniumUtils;
import org.openqa.selenium.By;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage extends WebPage {

    public final static By usernameInput = By.xpath("//input[@id='username']");
    public final static By passwordInput = By.xpath("//input[@id='password']");
    public final static By loginButton = By.xpath("//button[@type='submit']");

    public LoginPage() {
        driver.get(URL + "/login");
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

    public LoginPage alertBoxHasMsg(String msg){
        assertEquals(msg, SeleniumUtils.getElement(alertBox).getText());
        return this;
    }
}
