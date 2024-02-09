package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.gym.GymPlan;
import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.service.impl.GymService;
import com.jalizadeh.todocial.service.impl.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Search Controller Tests")
class SearchControllerTest {

    private final String AUTH = "admin:12345";
    private final String BASE_URL = "/search";
    private final String VALID_QUERY = "my";
    private final String INVALID_QUERY = "";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;

    @MockBean
    private GymService gymService;

    @Test
    @DisplayName("Search in target: Todo using valid query")
    void searchTodo_validQuery() throws Exception {
        List<Todo> mockTodos = Arrays.asList(new Todo(), new Todo());
        when(todoService.searchAllTodosByLoggedinUser(VALID_QUERY)).thenReturn(mockTodos);

        mvc.perform(get(BASE_URL).param("target", "todo").param("q", VALID_QUERY)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(AUTH.getBytes())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("settings"))
                .andExpect(model().attribute("PageTitle", "Search in TODO"))
                .andExpect(model().attribute("target", "TODO"))
                .andExpect(model().attribute("query", VALID_QUERY))
                .andExpect(model().attribute("items", mockTodos))
                .andExpect(view().name("search/todo"));

        verify(todoService, times(1)).searchAllTodosByLoggedinUser(VALID_QUERY);
    }

    @Test
    @DisplayName("Search in default target (Todo) with empty query")
    void searchDefaultTarget_EmptyQuery() throws Exception {
        List<Todo> mockTodos = Arrays.asList(new Todo(), new Todo());
        when(todoService.searchAllTodosByLoggedinUser("")).thenReturn(mockTodos);

        mvc.perform(get(BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(AUTH.getBytes())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("settings"))
                .andExpect(model().attribute("PageTitle", "Search in TODO"))
                .andExpect(model().attribute("target", "TODO"))
                .andExpect(model().attribute("query", ""))
                .andExpect(model().attribute("items", mockTodos))
                .andExpect(view().name("search/todo"));

        verify(todoService, times(1)).searchAllTodosByLoggedinUser("");
    }

    @Test
    @DisplayName("Search in target: Gym using valid query")
    void searchGym_validQuery() throws Exception {
        List<GymPlan> mockTodos = Arrays.asList(new GymPlan(), new GymPlan());
        when(gymService.searchAllPlans(VALID_QUERY)).thenReturn(mockTodos);

        mvc.perform(get(BASE_URL).param("target", "gym").param("q", VALID_QUERY)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(AUTH.getBytes())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("settings"))
                .andExpect(model().attribute("PageTitle", "Search in GYM"))
                .andExpect(model().attribute("target", "GYM"))
                .andExpect(model().attribute("query", VALID_QUERY))
                .andExpect(model().attribute("items", mockTodos))
                .andExpect(view().name("search/gym"));

        verify(gymService, times(1)).searchAllPlans(VALID_QUERY);
    }

    @Test
    @DisplayName("Search in target: Invalid - 302 Redirected")
    void search_InvalidTarget() throws Exception {
        mvc.perform(get(BASE_URL).param("target", "INVALID").param("q", VALID_QUERY)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(AUTH.getBytes())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andExpect(flash().attribute("exception", "The search target is not correct, it should be 'todo' or 'gym'."));
    }

    @Test
    @DisplayName("Test query sanitization")
    void testSanitizationOfQuery() throws Exception {
        String query = "<script>alert('XSS');</script>";
        String querySanitized = "&lt;script&gt;alert(&#39;XSS&#39;);&lt;/script&gt;";

        mvc.perform(get(BASE_URL).param("target", "todo").param("q", query)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(AUTH.getBytes())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("query", querySanitized));
    }
}