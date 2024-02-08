package com.jalizadeh.todocial.ui.views;

import com.jalizadeh.todocial.ui.common.WebPage;
import com.jalizadeh.todocial.ui.utils.SeleniumUtils;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

public class HomePage extends WebPage {

    //top menu
    public final static By homeBtn = By.xpath("//a[@class='navbar-brand' and @href='/']");
    public final static By loginLink = By.xpath("//a[@class='nav-link' and @href='/login']");
    public final static By signupLink = By.xpath("//a[@class='nav-link' and @href='/signup']");

    // top menu - logged in user
    public final static By meButton = By.xpath("//a[@class='navbar-brand' and @href='/me']");
    public final static By todoButton = By.xpath("//a[@class='navbar-brand' and @href='/todos']");
    public final static By gymButton = By.xpath("//a[@class='navbar-brand' and @href='/gym']");

    //footer
    public final static By footer = By.xpath("//footer[@id='sticky-footer']");
    public final static By footer_copyright = By.xpath("//div[@id='footer-copyright']");
    public final static By footer_language = By.xpath("//div[@id='footer-language']");
    public final static By footer_language_en = By.xpath("//a[@id='langEN']");
    public final static By footer_language_it = By.xpath("//a[@id='langEN']");

    public HomePage() {
        driver.get(URL);
    }

    public HomePage checkIfTopMenuExistsForAnonymousUser() {
        assertNotNull(SeleniumUtils.getElement(homeBtn));
        assertNotNull(SeleniumUtils.getElement(loginLink));
        assertNotNull(SeleniumUtils.getElement(signupLink));
        return this;
    }

    public HomePage checkIfTopMenuExistsForLoggedInUser() {
        assertNotNull(SeleniumUtils.getElement(homeBtn));
        assertNotNull(SeleniumUtils.getElement(meButton));
        assertNotNull(SeleniumUtils.getElement(todoButton));
        assertNotNull(SeleniumUtils.getElement(gymButton));

        assertTrue(SeleniumUtils.elementNotVisible(loginLink));
        assertTrue(SeleniumUtils.elementNotVisible(signupLink));
        return this;
    }

    public HomePage checkIfFooterExists() {
        assertNotNull(SeleniumUtils.getElement(footer));
        assertEquals("© 2022 ToDocial", SeleniumUtils.getElement(footer_copyright).getText());
        assertNotNull(SeleniumUtils.getElement(footer_language));
        assertNotNull(SeleniumUtils.getElement(footer_language_en));
        assertNotNull(SeleniumUtils.getElement(footer_language_it));
        return this;
    }

}
