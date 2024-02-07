package web.ui.tests;

import web.ui.common.WebPageTest;
import web.ui.views.HomePage;
import org.junit.jupiter.api.*;

@DisplayName("Homepage tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HomePageTest extends WebPageTest {

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
        new HomePage()
                .checkIfTopMenuExistsForAnonymousUser()
                //.checkIfContentExist()
                .checkIfFooterExists();
    }


}
