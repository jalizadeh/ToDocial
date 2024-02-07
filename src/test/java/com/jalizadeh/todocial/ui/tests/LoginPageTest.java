package com.jalizadeh.todocial.ui.tests;

import com.jalizadeh.todocial.ui.common.WebPageTest;
import com.jalizadeh.todocial.ui.views.LoginPage;
import com.jalizadeh.todocial.ui.views.HomePage;
import org.junit.jupiter.api.*;

import static com.jalizadeh.todocial.ui.utils.SeleniumUtils.refreshPage;

@Tag("e2e")
@Tag("int")
//@Tag("prod")
@DisplayName("User Login & Registration")
public class LoginPageTest extends WebPageTest {

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
        new LoginPage()
                .checkIfElementsExist()
                .fillFields(USERNAME, PASSWORD)
                .clickLoginButton();

        new HomePage()
                .checkIfTopMenuExistsForLoggedInUser();
    }

    @Test
    @DisplayName("Invalid username or password")
    void Test_3_2__InvalidUsernameOrPassword(){
        new LoginPage()
                .checkIfElementsExist()
                .fillFields("INVALID_" + USERNAME, PASSWORD)
                .clickLoginButton()
                .alertBoxHasMsg(ERROR_MSG);

        refreshPage();

        new LoginPage()
                .checkIfElementsExist()
                .fillFields(USERNAME, "INVALID_" + PASSWORD)
                .clickLoginButton()
                .alertBoxHasMsg(ERROR_MSG);

        refreshPage();

        new LoginPage()
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
