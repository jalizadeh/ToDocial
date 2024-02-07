package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.system.service.TokenService;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.model.ActivationToken;
import com.jalizadeh.todocial.web.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.Base64Utils;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("User API Tests")
class UserApiTest {

    private final static String BASE_URL = "/api/v1/user";
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    private final static Long INVALID_USER_ID = 99999L;
    private final static String INVALID_USER = "UNKNOWN_USER_1234567890";

    //data for creating new user
    private final String firstname  = "firstname_test";
    private final String lastname   = "lastname_test";
    private final String username   = "username_test";
    private final String email      = "email@test.com";
    private final String password   = "12345";

    private final String dataUser =
            "{   \"firstname\": \"" + firstname + "\",\n" +
            "    \"lastname\": \""  + lastname  + "\",\n" +
            "    \"username\": \""  + username  + "\",\n" +
            "    \"email\": \""     + email     + "\",\n" +
            "    \"password\": \""  + password  + "\" }"  ;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Test
    @Order(1)
    @DisplayName("Logged in user obtains his account info")
    void ApiUserTest_givenValidUsernamePassword_returnsLoggedInUserInfo() throws Exception {
        mvc.perform(get(BASE_URL + "/me")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Javad")))
                .andExpect(jsonPath("$.lastname", is("Alizadeh")))
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(jsonPath("$.email", is("jalizade3d@yahoo.com")))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.photo", is("javad.jpg")))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.followers", hasItem("mr.editor")))
                .andExpect(jsonPath("$.followers", hasSize(1)))
                .andExpect(jsonPath("$.followings", hasSize(5)));
    }

    @Test
    @Order(2)
    @Tag("api")
    @Tag("unit_test")
    @DisplayName("Access is denied with invalid credentials")
    void ApiUserTest_givenInvalidUserCredentials_deniesAccess() throws Exception {
        mvc.perform(get(BASE_URL + "/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    @DisplayName("Get all enabled users")
    void getUsers() throws Exception {
        mvc.perform(get(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(2))));
    }

    @Test
    @Order(4)
    @DisplayName("Get user info by username")
    void getUserByUsername() throws Exception {
        mvc.perform(get(BASE_URL + "/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.username", is("admin")));
    }

    @Test
    @Order(5)
    void getUserByUsernameNotFound() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(BASE_URL + "/" + INVALID_USER)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertEquals("Username not found: " + INVALID_USER, response.getErrorMessage());
    }

    @Test
    @Order(6)
    void getUserById() throws Exception {
        mvc.perform(get(BASE_URL + "/id/1")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @Order(7)
    void getUserByIdNotFound() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(BASE_URL + "/id/" + INVALID_USER_ID)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertEquals("User Id not found: " + INVALID_USER_ID, response.getErrorMessage());
    }

    @Test
    @Order(8)
    void createUser_valid() throws Exception {
        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataUser))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.firstname", is(firstname)))
                .andExpect(jsonPath("$.lastname", is(lastname)))
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.enabled", is(false)))
                .andExpect(jsonPath("$.photo", is("default.jpg")))
                .andExpect(jsonPath("$.followers").isArray())
                .andExpect(jsonPath("$.followers", hasSize(0)))
                .andExpect(jsonPath("$.followings").isArray())
                .andExpect(jsonPath("$.followings", hasSize(0)))
                .andDo(MockMvcResultHandlers.print());

        User user = userService.findByUsername(username);
        ActivationToken token = tokenService.findByUser(user);
        assertNotNull(token);
    }

    @Test
    @Order(9)
    void createUser_existingUsername() throws Exception {
        setupTestUser();

        String response = mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataUser))
                .andExpect(status().isConflict())
                .andReturn().getResponse().getContentAsString();

        assertEquals("The username already exists", response);
    }

    @Test
    @Order(10)
    @Disabled
    void activateUser() {
    }

    @Test
    @Order(11)
    @Disabled
    void getActivationToken() {
    }

    @Test
    @Order(12)
    void deleteUser() throws Exception {
        setupTestUser();

        mvc.perform(delete(BASE_URL + "/" + username)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        assertFalse(userService.findByUsername(username).isEnabled());
    }

    @Test
    @Order(13)
    void deleteUserFromDB() throws Exception {
        setupTestUser();

        mvc.perform(delete(BASE_URL + "/" + username + "/db")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        assertThrows(RuntimeException.class, () -> userService.findByUsername(username));

        MockHttpServletResponse response = mvc.perform(get(BASE_URL + "/" + INVALID_USER)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertEquals("Username not found: " + INVALID_USER, response.getErrorMessage());
    }

    private void setupTestUser() throws Exception {
        try {
            userService.hardDelete(username);
        } catch (Exception e){
            //do nothing, the user is already deleted
        }

        createUser_valid();
    }
}