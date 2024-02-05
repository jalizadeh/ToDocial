package com.jalizadeh.todocial.web.ui.tests;

import com.jalizadeh.todocial.web.ui.common.UITest;
import com.jalizadeh.todocial.web.ui.manager.SeleniumDriverManager;
import com.jalizadeh.todocial.web.ui.views.HomePage;
import com.jalizadeh.todocial.web.ui.views.LoginPage;
import org.junit.jupiter.api.*;

import static com.jalizadeh.todocial.web.ui.utils.SeleniumUtils.refreshPage;

@Tag("e2e")
@Tag("int")
//@Tag("prod")
@DisplayName("User Login & Registration")
public class LoginPageTest extends UITest {

    private final String USERNAME = "admin";
    private final String PASSWORD = "12345";
    private final String ERROR_MSG = "Username or Password is not correct.";

    @BeforeEach
    void beforeEach() {
        setup();
    }

    @AfterEach
    void afterEach() {
        teardown();
    }

    @Test
    @DisplayName("Valid username & email")
    void Test_3_1__ValidUsernameAndPassword() {
        new LoginPage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfElementsExist()
                .fillFields(USERNAME, PASSWORD)
                .clickLoginButton();

        new HomePage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfTopMenuExistsForLoggedInUser();
    }

    @Test
    @DisplayName("Invalid username or password")
    void Test_3_2__InvalidUsernameOrPassword(){
        new LoginPage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfElementsExist()
                .fillFields("INVALID_" + USERNAME, PASSWORD)
                .clickLoginButton()
                .alertBoxHasMsg(ERROR_MSG);

        refreshPage();

        new LoginPage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfElementsExist()
                .fillFields(USERNAME, "INVALID_" + PASSWORD)
                .clickLoginButton()
                .alertBoxHasMsg(ERROR_MSG);

        refreshPage();

        new LoginPage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfElementsExist()
                .fillFields("INVALID_" + USERNAME, "INVALID_" + PASSWORD)
                .clickLoginButton()
                .alertBoxHasMsg(ERROR_MSG);
    }

    @Disabled("UI doesnt support yet")
    @Test
    @DisplayName("Missing username or password")
    void Test_3_3__MissingUsernameOrPassword(){
        //
    }

}
