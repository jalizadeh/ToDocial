package web.controller;

import com.jalizadeh.todocial.web.controller.HomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Homepage controller tests")
@Tag("unit_test")
class HomeControllerTest {

    @Autowired
    HomeController homeController;

    @Test
    void showHomePage() {
        ModelMap modelMap = new ModelMap();
        String viewName = homeController.showHomePage(modelMap);

        assertEquals("home", viewName);
        //TODO
        //todo
        //users
        //settings
        assertEquals("Home", modelMap.get("PageTitle"));
    }
}