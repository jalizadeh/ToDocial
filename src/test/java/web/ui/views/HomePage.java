package web.ui.views;

import web.ui.common.WebPage;
import web.ui.utils.SeleniumUtils;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

public class HomePage extends WebPage {

    //top menu
    private final By homeBtn = By.xpath("//a[@class='navbar-brand' and @href='/']");
    private final By loginLink = By.xpath("//a[@class='nav-link' and @href='/login']");
    private final By signupLink = By.xpath("//a[@class='nav-link' and @href='/signup']");

    // top menu - logged in user
    private final By meButton = By.xpath("//a[@class='navbar-brand' and @href='/me']");
    private final By todoButton = By.xpath("//a[@class='navbar-brand' and @href='/todos']");
    private final By gymButton = By.xpath("//a[@class='navbar-brand' and @href='/gym']");

    //footer
    private final By footer = By.xpath("//footer[@id='sticky-footer']");
    private final By footer_copyright = By.xpath("//div[@id='footer-copyright']");
    private final By footer_language = By.xpath("//div[@id='footer-language']");
    private final By footer_language_en = By.xpath("//a[@id='langEN']");
    private final By footer_language_it = By.xpath("//a[@id='langEN']");

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
