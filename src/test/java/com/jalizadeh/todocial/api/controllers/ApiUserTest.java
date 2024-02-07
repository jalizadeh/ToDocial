package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.system.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiUserTest {

    private final static String BASE_URL = "/api/v1/user";
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    private final static Long INVALID_USER_ID = 99999L;
    private final static String INVALID_USER = "UNKNOWN_USER_1234567890";

    @Autowired
    private MockMvc mvc;

    @Mock
    private UserService userService;

    @Test
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
    @Tag("api")
    @Tag("unit_test")
    @DisplayName("User API : Access is denied with invalid credentials")
    void ApiUserTest_givenInvalidUserCredentials_deniesAccess() throws Exception {
        mvc.perform(get(BASE_URL + "/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUsers() throws Exception {
        mvc.perform(get(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(2))));
    }

    @Test
    void getUserByUsername() throws Exception {
        mvc.perform(get(BASE_URL + "/admin")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.username", is("admin")));
    }

    @Test
    void getUserByUsernameNotFound() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(BASE_URL + "/" + INVALID_USER)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertEquals("Username not found: " + INVALID_USER, response.getErrorMessage());
    }

    @Test
    void getUserById() throws Exception {
        mvc.perform(get(BASE_URL + "/id/1")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        MockHttpServletResponse response = mvc.perform(get(BASE_URL + "/id/" + INVALID_USER_ID)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        assertEquals("User Id not found: " + INVALID_USER_ID, response.getErrorMessage());
    }

    @Test
    @Disabled
    void createUser() {
    }

    @Test
    @Disabled
    void activateUser() {
    }

    @Test
    @Disabled
    void getActivationToken() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }

    @Test
    @Disabled
    void deleteUserFromDB() {
    }
}