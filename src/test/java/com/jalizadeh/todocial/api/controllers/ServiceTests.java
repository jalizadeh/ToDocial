package com.jalizadeh.todocial.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.VerificationToken;
import com.jalizadeh.todocial.web.repository.VerificationTokenRepository;

@DisplayName("User & Todo Services Tests")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test") // use separated DB for test
class ServiceTests {

	private final String baseUrl = "http://localhost";
	private final String userServicePath = "/user";
	private final String todoServicePath = "/todo";

	@LocalServerPort
	private int port;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private VerificationTokenRepository vtRepository;

	@Autowired
	private Environment env;

	@Autowired
	private TestRestTemplate rest;

	//Arrange User model
	private Long userId;
	private final String FIRSTNAME =  "firstname_test";
	private final String LASTNAME = "lastname_test";
	private final String USERNAME = "username_test";
	private final String EMAIL = "email@test.com";
	private final String PASSWORD = "12345";
	private TestModel_User_Request userRequest = new TestModel_User_Request(FIRSTNAME, LASTNAME, USERNAME, EMAIL, PASSWORD);
	private TestModel_User_Response userReponse;
	private ResponseEntity<TestModel_User_Response> userResponseEntity;
	
	//Arrange Todo model
	private Long todoId;
	private TestModel_Todo_Request todoRequest = new TestModel_Todo_Request(
			"TODO - auto-generated in test",
			"You can'\''t quantify the monitor without calculating the 1080p EXE circuit! The RSS port is down, input the haptic hard drive so we can connect the CSS bandwidth! Try to connect the SDD feed, maybe it will parse the multi-byte matrix!",
			"You can'\''t input the driver without copying the haptic TCP driver!");
	private TestModel_Todo_Response todoResponse;
	private ResponseEntity<TestModel_Todo_Response> todoResponseEntity;
	

	@BeforeAll
	void setup() {
		// delete previously broken generated test user, if exists
		deleteUser(userRequest.getUsername());
	}

	//------------------------------- Tests 1-to-19 are for User Service

	@Test
	@Order(1)
	@Tag("positive")
	@DisplayName("Create user and check in the DB")
	void createUser() {
		// create a new test user
		userResponseEntity = rest.postForEntity(getUrl_User(), getEntity(userRequest, true), TestModel_User_Response.class);
		assertNotNull(userResponseEntity);
		userReponse = userResponseEntity.getBody();
		assertEquals(userRequest.getFirstname(), userReponse.getFirstname());

		this.userId = userReponse.getId();

		User dbUser = userRepository.findById(userId).get();
		assertEquals(dbUser.getFirstname(), userReponse.getFirstname());
		assertEquals(dbUser.getLastname(), userReponse.getLastname());
		assertEquals(dbUser.getEmail(), userReponse.getEmail());
		assertEquals(dbUser.getPhoto(), "default.jpg");
		assertFalse(dbUser.isEnabled());

		System.err.println("User " + userRequest.getFirstname() + " created successfully");
	}

	@Test
	@Order(2)
	@Tag("positive")
	@DisplayName("Activate user after registration")
	void activateUser() {
		// check if activation code exists
		VerificationToken vToken = vtRepository.findByUserId(userId);
		assertNotNull(vToken, "Verification token doesnt exist");

		// get activation code
		ResponseEntity<TestModel_Token_Request> resp1 = rest.exchange(
				mkUrl(getUrl_User(), USERNAME, "activation_token"), HttpMethod.GET, getEntity(userRequest, false),
				TestModel_Token_Request.class);
		assertEquals(vToken.getToken(), resp1.getBody().getToken());

		// activate user
		ResponseEntity<String> resp2 = rest.postForEntity(
				mkUrl(getUrl_User(), USERNAME, "activate?token=" + vToken.getToken()), getEntity(userRequest, false), String.class);
		assertEquals("valid", resp2.getBody());

		// activation code should be removed
		assertNull(vtRepository.findByUserId(userId), "Verification token is not deleted after activation");
	}

	@Test
	@Order(3)
	@DisplayName("Get a user by username")
	void testUserService_whenAUsernameisGiven_returnsItsDetails() {
		ResponseEntity<TestModel_User_Response> response = rest.exchange(mkUrl(getUrl_User(), USERNAME), HttpMethod.GET,
				getEntity(userRequest, false), TestModel_User_Response.class);

		assertNotNull(response);
		assertEquals(userId, response.getBody().getId());
		assertEquals(userRepository.findById(userId).get().getId(), response.getBody().getId());
		assertEquals(USERNAME, response.getBody().getUsername());
		assertEquals(userRepository.findById(userId).get().getUsername(), response.getBody().getUsername());
	}

	@Test
	@Order(4)
	@DisplayName("Get list of all users")
	void testUserService_whenAskedForAllUsers_returnsListOfAllUsers() {
		ResponseEntity<List<TestModel_User_Request>> response = rest.exchange(getUrl_User(), HttpMethod.GET,
				getEntity(userRequest, false), new ParameterizedTypeReference<List<TestModel_User_Request>>() {
				});

		assertNotNull(response);
		assertTrue(response.getBody().size() > 0); // at least 1 user (the generated test user) exists
	}

	
	
	
	//------------------------------- Tests 20-to-40 are for Todo Service

	@Test
	@Order(20)
	@DisplayName("Create a Todo for existing user")
	void testTodoService_whenATodoIsCreated_returnsNewTodoDetails() {
		todoResponseEntity = rest.postForEntity(getUrl_Todo(), getEntity(todoRequest, USERNAME, PASSWORD, true), 
				TestModel_Todo_Response.class);
		todoResponse = todoResponseEntity.getBody();
		todoId = todoResponse.getId();

		assertEquals(todoRequest.getName(), todoResponse.getName());
		assertEquals(0, todoResponse.getLogs().size());
		assertEquals(0, todoResponse.getLike());
		assertFalse(todoResponse.isCompleted());
		assertFalse(todoResponse.isCanceled());
		assertFalse(todoResponse.isPublicc());
	}

	
	@Test
	@Order(40)
	@DisplayName("Delete a Todo from DB")
	void testTodoService_whenTodoIsDeleted_returnsNullOnDBCheck() {
		rest.exchange(mkUrl(getUrl_Todo(),String.valueOf(todoId),"db"), HttpMethod.DELETE, getEntity(todoRequest, USERNAME, PASSWORD, false), String.class);
		Todo deletedTodo = todoRepository.findById(todoId).orElse(null);
		assertNull(deletedTodo);
	}
	
	
	
	
	//------------------------------- Tests 90-to-100 are for tear down actions

	@Test
	@Order(90)
	@DisplayName("Deactivate a user by username")
	void testUserService_whenAUsernameisGiven_returnsIsEnabledFalse() {
		ResponseEntity<String> response = rest.exchange(mkUrl(getUrl_User(), USERNAME), HttpMethod.DELETE,
				getEntity(userRequest, false), String.class);

		assertNotNull(response);
		assertEquals("false", response.getBody());
		assertFalse(userRepository.findById(userId).get().isEnabled());
	}

	@Test
	@Order(91)
	@DisplayName("Delete user from DB")
	void testUserService_whenUserIsDeleted_returnsNullOnFindByUsername() {
		deleteUser(USERNAME);
	}

	/**
	 * Deletes the user via API and verifies by checking DB
	 */
	private void deleteUser(String username) {
		rest.exchange(mkUrl(getUrl_User(), username, "db"), HttpMethod.DELETE, getEntity(userRequest, false), String.class);
		assertNull(userRepository.findByUsername(username));
	}

	// make url using given parameters
	private String mkUrl(String... list) {
		return Arrays.asList(list).stream().collect(Collectors.joining("/"));
	}

	// get base URL of API service
	private String getUrl_User() {
		return this.baseUrl + ":" + this.port + env.getProperty("api.rootUrl") + userServicePath;
	}

	private String getUrl_Todo() {
		return this.baseUrl + ":" + this.port + env.getProperty("api.rootUrl") + todoServicePath;
	}

	// generate HttpEntity w/ or w/o body
	private <T> HttpEntity<T> getEntity(T model, boolean body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(env.getProperty("api.auth_user"), env.getProperty("api.auth_pass"));

		return body ? new HttpEntity<>(model, headers) : new HttpEntity<>(headers);
	}

	// generate HttpEntity w/ or w/o body with custom Basic Auth header
	private <T> HttpEntity<T> getEntity(T model, String username, String password, boolean body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(username, password);

		return body ? new HttpEntity<>(model, headers) : new HttpEntity<>(headers);
	}

}
