package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.service.impl.TodoService;
import com.jalizadeh.todocial.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
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

    @MockBean
    private TodoService todoService;

    @BeforeEach
    void setupUsers(){
        targetUser.setFirstname("Micheal");
        targetUser.setLastname("J. Jackson [USER]");
        targetUser.setUsername("mjackson.user");

        loggedinUser.setFirstname("Javad");
        loggedinUser.setLastname("Alizadeh");
        loggedinUser.setUsername("admin");
        loggedinUser.setFollowings(Arrays.asList(targetUser));
        loggedinUser.setFollowers(new ArrayList<>());
    }

    @Test
    @DisplayName("Redirect to error page on invalid username")
    void whenUserDoesntExist_redirectToHomePage() throws Exception {
        mvc.perform(get("/@" + INVALID_USERNAME))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Show a user's page to an anonymous user")
    void showValidPageToNotLoggedInUser() throws Exception {
        when(userService.findByUsername(targetUser.getUsername())).thenReturn(targetUser);
        when(userService.isUserAnonymous()).thenReturn(true);

        mvc.perform(get("/@" + targetUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", targetUser))
                .andExpect(model().attribute("todos", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("gym", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + " | @" + targetUser.getUsername()))
                .andExpect(view().name("public-page"));
    }

    @Test
    @DisplayName("Show a user's page to a logged in user that is following him")
    void showAValidPageToLoggedInUserThatIsFollowing() throws Exception {
        when(userService.getAuthenticatedUser()).thenReturn(loggedinUser);
        when(userService.findByUsername(targetUser.getUsername())).thenReturn(targetUser);
        when(userService.isUserAnonymous()).thenReturn(false);

        mvc.perform(get("/@" + targetUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", targetUser))
                .andExpect(model().attribute("todos", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("gym", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + " | @" + targetUser.getUsername()))
                .andExpect(view().name("public-page"));
    }

    @Test
    @DisplayName("Show a user's page to a logged in user that is not following him")
    void showAValidPageToLoggedInUserThatIsNotFollowing() throws Exception {
        // in reality a user can not follow himself, but for this test, it is fine, as long as the loggedin user != target user
        loggedinUser.setFollowings(Arrays.asList(loggedinUser));

        when(userService.getAuthenticatedUser()).thenReturn(loggedinUser);
        when(userService.findByUsername(targetUser.getUsername())).thenReturn(targetUser);
        when(userService.isUserAnonymous()).thenReturn(false);

        mvc.perform(get("/@" + targetUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", targetUser))
                .andExpect(model().attribute("todos", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("gym", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + " | @" + targetUser.getUsername()))
                .andExpect(view().name("public-page"));
    }

    @Test
    @DisplayName("Show loggedin user's page to him")
    void showMyPage() throws Exception {
        when(userService.getAuthenticatedUser()).thenReturn(loggedinUser);
        when(userService.findByUsername("admin")).thenReturn(loggedinUser);
        when(todoService.findAllByUser(loggedinUser)).thenReturn(new ArrayList<>());
        when(userService.isUserAnonymous()).thenReturn(false);

        mvc.perform(get("/@admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", loggedinUser))
                .andExpect(model().attribute("todos", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("gym", allOf(
                        instanceOf(List.class),
                        hasSize(0)
                )))
                .andExpect(model().attribute("PageTitle", loggedinUser.getFirstname() + " " + loggedinUser.getLastname() + " | @" + loggedinUser.getUsername()))
                .andExpect(view().name("public-page"));
    }

}