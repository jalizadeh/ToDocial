package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.RequestDispatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * https://stackoverflow.com/questions/52925700/cannot-properly-test-errorcontroller-spring-boot
 */
@Disabled("NOT Supported by MockMvc")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Exception Controller Tests")
class ExceptionControllerTest {

    private final String AUTH = "admin:12345";
    private final String BASE_URL = "/error";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SettingsGeneralConfig settings;


    @Test
    void getErrorPath() {
    }

    @Test
    void testHandleNotFoundError() throws Exception {
        mvc.perform(get(BASE_URL).requestAttr(RequestDispatcher.ERROR_STATUS_CODE, "404"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("PageTitle", "404 : 404"))
                .andExpect(model().attribute("exception", "404 : NOT FOUND<br/>The web page is not found"));
    }

}