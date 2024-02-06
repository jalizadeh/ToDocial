package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.api.controllers.dto.UserDto;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.model.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Base64Utils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class ApiUserTest {

    private final static String BASE_URL = "/api/v1/user";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void getMeForAuthenticatedUser() throws Exception {
        //Arrange
        //new UserDTO
        //{"id":1,"firstname":"Javad","lastname":"Alizadeh","username":"admin","email":"jalizade3d@yahoo.com","enabled":true,"photo":"javad.jpg","followers":["mr.editor"],"followings":["mr.editor","mjackson.user","Aaron_Casper94","Jasper79","Emerald.Predovic"]}

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstname("Javad");

        User user = new User();
        user.setId(1L);
        user.setFirstname("Javad");

        //when(userService.GetAuthenticatedUser()).thenReturn(user);

        //Action
        mvc.perform(get(BASE_URL + "/me")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:12345".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));

        verify(userService, times(1)).GetAuthenticatedUser();

    }

    @Test
    void getMeForNotAuthenticatedUser() throws Exception {
        //---arrange

        //---action
        mvc.perform(get(BASE_URL + "/me"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));

        //---assertion
        //as the user is not authorized, this method is never invoked
        verify(userService, times(0)).GetAuthenticatedUser();
    }

    @Test
    void getUsers() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void createUser() {
    }

    @Test
    void activateUser() {
    }

    @Test
    void getActivationToken() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteUserFromDB() {
    }
}