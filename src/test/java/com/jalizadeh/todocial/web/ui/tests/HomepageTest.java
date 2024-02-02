package com.jalizadeh.todocial.web.ui.tests;

import com.jalizadeh.todocial.web.ui.common.UITest;
import com.jalizadeh.todocial.web.ui.manager.SeleniumDriverManager;
import com.jalizadeh.todocial.web.ui.views.Homepage;
import org.junit.jupiter.api.*;

@DisplayName("Homepage tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomepageTest extends UITest {

    @BeforeEach
    void beforeEach() {
        setup();
    }

    @AfterEach
    void afterEach() {
        teardown();
    }

    @Test
    @DisplayName("Top menu and footer for anonymous user")
    void HomepageTest_whenUserIsAnonymous_showBasicMenu() {
        new Homepage(SeleniumDriverManager.getDriverInThreadLocal())
                .checkIfTopMenuExistsForAnonymousUser()
                //.checkIfContentExist()
                .checkIfFooterExists();
    }


}
