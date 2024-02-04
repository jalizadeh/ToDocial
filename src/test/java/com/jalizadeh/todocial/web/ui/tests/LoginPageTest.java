package com.jalizadeh.todocial.web.ui.tests;

import com.jalizadeh.todocial.web.ui.common.UITest;
import com.jalizadeh.todocial.web.ui.manager.SeleniumDriverManager;
import com.jalizadeh.todocial.web.ui.views.HomePage;
import com.jalizadeh.todocial.web.ui.views.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginPageTest extends UITest {

    @BeforeEach
    void beforeEach() {
        setup();
    }

    @AfterEach
    void afterEach() {
        teardown();
    }

    @Test
    void successfulUserLogin() throws InterruptedException {
        new LoginPage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfElementsExist()
                .fillFields("admin", "12345")
                .clickLoginButton();
        Thread.sleep(3000);

        new HomePage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfTopMenuExistsForLoggedInUser();
    }
}
