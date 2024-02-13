package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.FlashMessage;
import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.user.PasswordResetToken;
import com.jalizadeh.todocial.model.user.SecurityQuestionDefinition;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.service.impl.TokenService;
import com.jalizadeh.todocial.service.impl.UserService;
import com.jalizadeh.todocial.service.registration.OnPasswordResetEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Login & Signup Controller Tests")
class LoginSignupControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @MockBean
    private SettingsGeneralConfig settings;


    @BeforeEach
    void setup(){
        // Mock the behavior of the SettingsGeneralConfig bean to satisfy AppLocaleResolver
        when(settings.getLanguage()).thenReturn("en_US");
    }

    @Test
    void showLoginPageToAnonymousUser() throws Exception {
        when(userService.isUserAnonymous()).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("flash", "Flash msg");

        mvc.perform(get("/login").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("settings", instanceOf(SettingsGeneralConfig.class)))
                .andExpect(model().attribute("PageTitle", "Log in"))
                .andExpect(model().attribute("user", instanceOf(User.class)))
                .andExpect(model().attribute("flash", "Flash msg"))
                .andExpect(view().name("login"));

        //flash message in session is removed
        assertNull(session.getAttribute("flash"));
    }

    @Test
    void redirectLoggedInUserToHomePage() throws Exception {
        when(userService.isUserAnonymous()).thenReturn(false);

        mvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void logout() throws Exception {
        //after logout, it is redirected
        mvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));

        //being redirected to /login?logout=true will show a message to user
        when(userService.isUserAnonymous()).thenReturn(true);

        mvc.perform(get("/login").param("logout", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("message", instanceOf(FlashMessage.class)));
    }

    @Test
    void showForgotPasswordPage() throws Exception {
        mvc.perform(get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("settings", instanceOf(SettingsGeneralConfig.class)))
                .andExpect(model().attribute("PageTitle", "Forgot Password"))
                .andExpect(view().name("forgot-password"));
    }

    @Test
    void forgotPasswordSubmit_NotExistingUser() throws Exception {
        when(userService.findByEmail(any())).thenReturn(null);

        mvc.perform(post("/forgot-password").param("email", "invalid_email@y.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("flash"))
                .andExpect(redirectedUrl("/forgot-password"));
    }

    @Test
    @Disabled("dont know how to impl")
    void forgotPasswordSubmit_ExistingUser() throws Exception {
        User user = new User();
        user.setEmail("user@mail.com");
        PasswordResetToken prt = new PasswordResetToken("12345", user, new Date());

        when(userService.findByEmail(any())).thenReturn(user);
        doNothing().when(tokenService).savePRToken(any());
        doNothing().when(eventPublisher).publishEvent(new OnPasswordResetEvent(prt, "/url", new Locale("en_US")));

        mvc.perform(post("/forgot-password").param("email", "valid@y.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("flash"))
                .andExpect(redirectedUrl("/forgot-password"));
    }

    @Test
    @Disabled("dont know how to impl")
    void forgotPasswordSubmit_ExistingUser2() throws Exception {
        // Create a mock for the ApplicationEventPublisher
        ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

        // Mock the UserService.findByEmail method to return a non-null user
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.findByEmail(any())).thenReturn(new User());

        // Mock the TokenService and other dependencies
        doNothing().when(tokenService).savePRToken(any());

        // Create an instance of your controller and inject the mock eventPublisher
        LoginSignupController controller = new LoginSignupController();

        // Perform the request
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/forgot-password").param("email", "valid@y.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("flash"))
                .andExpect(redirectedUrl("/forgot-password"));

        // Verify that the eventPublisher.publishEvent method was not called
        verify(eventPublisher, never()).publishEvent(any(OnPasswordResetEvent.class));
    }

    @Test
    @Disabled
    void showResetPasswordConfirmed() {

    }

    @Test
    @Disabled
    void changePassword() {
    }

    @Test
    void showSignupPageToAnonymousUser() throws Exception {
        when(userService.isUserAnonymous()).thenReturn(true);

        mvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("settings", instanceOf(SettingsGeneralConfig.class)))
                .andExpect(model().attribute("PageTitle", "Sign up"))
                .andExpect(model().attribute("user", instanceOf(User.class)))
                .andExpect(model().attribute("securityQuestions", allOf(
                        instanceOf(List.class),
                        hasSize(greaterThan(0)),
                        everyItem(instanceOf(SecurityQuestionDefinition.class))
                )));

    }

    @Test
    void showSignupPageToLoggedinUser() throws Exception {
        when(userService.isUserAnonymous()).thenReturn(false);

        mvc.perform(get("/signup"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @Disabled
    void registerUserAccount() {
    }

    @Test
    @Disabled
    void confirmRegistration() {
    }
}