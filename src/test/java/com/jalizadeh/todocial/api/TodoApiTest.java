package com.jalizadeh.todocial.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Todo API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoApiTest {

    @Test
    void getAllTodo() {
    }

    @Test
    void getUserTodo() {
    }

    @Test
    void testGetUserTodo() {
    }

    @Test
    void createTodo() {
    }

    @Test
    void cancelTodo() {
    }

    @Test
    void deleteTodoFromDB() {
    }

    @Test
    void createTodoLog() {
    }

    @Test
    void deleteTodoLog() {
    }
}