package com.jalizadeh.todocial.ui.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import com.jalizadeh.todocial.ui.manager.SeleniumDriverManager;
import com.jalizadeh.todocial.ui.views.HomePage;
import com.jalizadeh.todocial.ui.views.LoginPage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.jalizadeh.todocial.ui.utils.SeleniumUtils.*;

public class UISteps {

    private WebDriver driver;

    @Before
    public void setup(){
        SeleniumDriverManager.initDriver();
        driver = SeleniumDriverManager.getDriverInThreadLocal();
    }

    @After
    public void teardown(){
        SeleniumDriverManager.getDriverInThreadLocal().quit();
    }

    @Given("navigate to page {string}")
    public void navigateToPage(String url) {
        driver.get("http://localhost:8080/" + url);
    }


    @When("fill username field with {string}")
    public void fillUsernameFieldWith(String username) {
        writeText(LoginPage.usernameInput, username);
    }

    @When("fill password field with {string}")
    public void fillPasswordFieldWith(String password) {
        writeText(LoginPage.passwordInput, password);
    }

    @When("click login button")
    public void clickLoginButton() {
        getElement(LoginPage.loginButton).click();
    }


    @Then("check if has full access to top menu items")
    public void checkIfHasFullAccessToTopMenuItems() {
        assertNotNull(getElement(HomePage.homeBtn));
        assertNotNull(getElement(HomePage.meButton));
        assertNotNull(getElement(HomePage.todoButton));
        assertNotNull(getElement(HomePage.gymButton));

        assertTrue(elementNotVisible(HomePage.loginLink));
        assertTrue(elementNotVisible(HomePage.signupLink));
    }
}
