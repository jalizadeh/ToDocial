package com.jalizadeh.todocial.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalizadeh.todocial.model.todo.dto.InputTodo;
import com.jalizadeh.todocial.model.todo.dto.TodoDto;
import org.apache.hc.core5.http.HttpHeaders;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Base64Utils;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Todo API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoApiTest {

    private final static String BASE_URL = "/api/v1/todo";
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    private final String testTodoName = "TODO - test";
    private final String testTodoReason = "Reason - test";
    private final String testTodoDescription = "Description - test";

    //needed for cancelling/deleting the created one
    private Long createdTodoId = 0L;
    private Long createdTodoLogId = 0L;

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    void findById_invalid() throws Exception {
        mvc.perform(get(BASE_URL + "/id/9999")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    void getAllTodo() throws Exception {
        mvc.perform(get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(greaterThan(3))));
    }

    @Test
    @Order(3)
    void getUserTodo() throws Exception {
        mvc.perform(get(BASE_URL + "/" + USERNAME)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(greaterThan(5))));

    }

    @Test
    @Order(4)
    void getCurrentUserTodo() throws Exception {
        mvc.perform(get(BASE_URL + "/me")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(greaterThan(5))));
    }

    @Test
    @Order(5)
    void createTodo() throws Exception {
        InputTodo inputTodo = new InputTodo(testTodoName, testTodoDescription, testTodoReason);

        MvcResult result = mvc.perform(post(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputTodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(greaterThan(1))))
                .andExpect(jsonPath("$.name", is(testTodoName)))
                .andExpect(jsonPath("$.reason", is(testTodoReason)))
                .andExpect(jsonPath("$.description", is(testTodoDescription)))
                .andExpect(jsonPath("$.logs", hasSize(0)))
                .andExpect(jsonPath("$.like", is(0)))
                .andExpect(jsonPath("$.completed", is(false)))
                .andExpect(jsonPath("$.canceled", is(false)))
                .andExpect(jsonPath("$.publicc", is(false)))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        TodoDto createdTodo = new ObjectMapper().readValue(responseJson, TodoDto.class);
        createdTodoId = createdTodo.getId();

        //get the created todo
        mvc.perform(get(BASE_URL + "/id/" + createdTodoId)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(createdTodoId));
    }

    @Test
    @Order(6)
    @Disabled
    void createTodoLog() {
    }

    @Test
    @Order(7)
    @Disabled
    void deleteTodoLog() {
    }

    @Test
    @Order(8)
    @Disabled
    void cancelTodo() {
        System.out.println("canceltodo: " +  createdTodoId);
    }

    @Test
    @Order(9)
    void deleteTodoFromDB_valid() throws Exception {
        mvc.perform(delete(BASE_URL + "/" + createdTodoId + "/db")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((USERNAME + ":" + PASSWORD).getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

    }

}