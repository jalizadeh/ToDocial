package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.service.impl.UserService;
import org.apache.hc.core5.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Public Page Controller Tests")
class PublicPageControllerTest {

    private final static String AUTH = "admin:12345";
    private final static String INVALID_USERNAME = "user_not_exists";

    private User loggedinUser = new User();
    private User targetUser = new User();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setupUsers(){
        loggedinUser.setFirstname("Javad");
        loggedinUser.setLastname("Alizadeh");
        loggedinUser.setUsername("admin");

        targetUser.setFirstname("Micheal");
        targetUser.setLastname("J. Jackson [USER]");
        targetUser.setUsername("mjackson.user");
    }

    @Test
    @DisplayName("Redirected to error page on invalid username")
    void usernameDoesntExist() throws Exception {
        mvc.perform(get("/@" + INVALID_USERNAME))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Show user's page to any anonymous user")
    void showValidPageToNotLoggedInUser() throws Exception {
        when(userService.findByUsername(targetUser.getUsername())).thenReturn(targetUser);
        when(userService.isUserAnonymous()).thenReturn(true);

        mvc.perform(get("/@" + targetUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", targetUser))
                //.andExpect(model().attribute("todos", null))
                //.andExpect(model().attribute("gym", null))
                .andExpect(model().attribute("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + " | @" + targetUser.getUsername()))
                .andExpect(view().name("public-page"));
    }

    @Test
    @Disabled("loggedin user is not handled")
    void showAValidPageToLoggedInUser() throws Exception {
        when(userService.getAuthenticatedUser()).thenReturn(loggedinUser);
        when(userService.findByUsername(targetUser.getUsername())).thenReturn(targetUser);
        when(userService.isUserAnonymous()).thenReturn(false);

        mvc.perform(get("/@" + targetUser.getUsername())
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(AUTH.getBytes())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", targetUser))
                //.andExpect(model().attribute("todos", null))
                //.andExpect(model().attribute("gym", null))
                .andExpect(model().attribute("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + " | @" + targetUser.getUsername()))
                .andExpect(view().name("public-page"));
    }

    @Test
    @Disabled("loggedin user is not handled")
    void showMyPage(){

    }

}