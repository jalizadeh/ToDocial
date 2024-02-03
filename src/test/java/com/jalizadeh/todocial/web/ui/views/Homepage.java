package com.jalizadeh.todocial.web.ui.views;

import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.ui.utils.SeleniumUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Homepage {

    private WebDriver driver;

    //top menu
    private final By homeBtn = By.xpath("//a[@class='navbar-brand' and @href='/']");
    private final By loginLink = By.xpath("//a[@class='nav-link' and @href='/login']");
    private final By signupLink = By.xpath("//a[@class='nav-link' and @href='/signup']");

    //footer
    private final By footer = By.xpath("//footer[@id='sticky-footer']");
    private final By footer_copyright = By.xpath("//div[@id='footer-copyright']");
    private final By footer_language = By.xpath("//div[@id='footer-language']");
    private final By footer_language_en = By.xpath("//a[@id='langEN']");
    private final By footer_language_it = By.xpath("//a[@id='langEN']");

    public Homepage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://localhost:8080");
    }

    @Step("Check menu items when user is anonymous (not logged in)")
    public Homepage checkIfTopMenuExistsForAnonymousUser() {
        assertNotNull(SeleniumUtils.getElement(homeBtn));
        assertNotNull(SeleniumUtils.getElement(loginLink));
        assertNotNull(SeleniumUtils.getElement(signupLink));
        return this;
    }

    @Step("Check footer items")
    public Homepage checkIfFooterExists() {
        assertNotNull(SeleniumUtils.getElement(footer));
        assertEquals("© 2022 ToDocial", SeleniumUtils.getElement(footer_copyright).getText());
        assertNotNull(SeleniumUtils.getElement(footer_language));
        assertNotNull(SeleniumUtils.getElement(footer_language_en));
        assertNotNull(SeleniumUtils.getElement(footer_language_it));
        return this;
    }

}
