package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.api.controllers.models.*;
import com.jalizadeh.todocial.system.repository.TodoLogRepository;
import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.TodoLog;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.ActivationToken;
import com.jalizadeh.todocial.web.repository.ActivationTokenRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@DisplayName("User & Todo Services Tests")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test") // use separated DB for test
@ExtendWith(MockitoExtension.class)
class ServiceTestsMockito {

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
	private TodoLogRepository todoLogRepository;

	@Autowired
	private ActivationTokenRepository vtRepository;

	@Autowired
	private Environment env;

	@Autowired
	private TestRestTemplate rest;

	@Mock
	User user;

	@Mock
	Todo todo;

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
	private TestModel_Todo_Request todoRequest =
			new TestModel_Todo_Request("TODO - auto-generated in test", "You can read","You can do");
	private TestModel_Todo_Response todoResponse;
	private ResponseEntity<TestModel_Todo_Response> todoResponseEntity;
	
	//Arrange TodoLog model
	private Long todoLogId;
	private TestModel_TodoLog_Request todoLogRequest = new TestModel_TodoLog_Request(); //log filled later
	private TestModel_TodoLog_Response todoLogResponse;
	private ResponseEntity<TestModel_TodoLog_Response> todoLogResponseEntity;

	/*
	@BeforeAll
	void setup() {
		// delete previously broken generated test user, if exists
		deleteUser(userRequest.getUsername());
	}

	 */

	//------------------------------- Tests 1-to-19 are for User Service

	@Test
	@Order(1)
	@Tag("positive")
	@DisplayName("Create user and check in the DB")
	void testUserService_givenUserDetailsAsRequestBody_returnsGeneratedUserDetails() {



		// create a new test user
		userResponseEntity = rest.postForEntity(getUrl_User(), getEntity(userRequest, false), TestModel_User_Response.class);
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
	void testUserService_givenUsername_returnsActivatedUser() {
		// check if activation code exists
		ActivationToken vToken = vtRepository.findByUserId(userId);
		assertNotNull(vToken, "Verification token doesnt exist");

		// get activation code
		ResponseEntity<TestModel_Token_Request> r1 = rest.exchange(
				mkUrl(getUrl_User(), USERNAME, "activation_token"), HttpMethod.GET, getEntity(null, false),
				TestModel_Token_Request.class);
		assertEquals(vToken.getToken(), r1.getBody().getToken());

		// activate user
		ResponseEntity<String> r2 = rest.postForEntity(
				mkUrl(getUrl_User(), USERNAME, "activate?token=" + vToken.getToken()), 
				getEntity(null, false), String.class);
		assertEquals("valid", r2.getBody());

		// activation code should be removed
		assertNull(vtRepository.findByUserId(userId), "Verification token is not deleted after activation");
	}

	@Test
	@Order(3)
	@DisplayName("Get a user by username")
	void testUserService_givenUsername_returnsItsDetails() {
		ResponseEntity<TestModel_User_Response> r = rest.exchange(mkUrl(getUrl_User(), USERNAME), HttpMethod.GET,
				getEntity(null, false), TestModel_User_Response.class);

		assertNotNull(r);
		assertEquals(userId, r.getBody().getId());
		assertEquals(userRepository.findById(userId).get().getId(), r.getBody().getId());
		assertEquals(USERNAME, r.getBody().getUsername());
		assertEquals(userRepository.findById(userId).get().getUsername(), r.getBody().getUsername());
	}

	@Test
	@Order(4)
	@DisplayName("Get list of all users")
	void testUserService_whenAskedForAllUsers_returnsListOfAllUsers() {
		ResponseEntity<List<TestModel_User_Request>> r = rest.exchange(getUrl_User(), HttpMethod.GET,
				getEntity(null, false), new ParameterizedTypeReference<List<TestModel_User_Request>>() {});

		assertNotNull(r);
		assertTrue(r.getBody().size() > 0); // at least 1 user (the generated test user) exists
	}

	
	
	
	//------------------------------- Tests 20-to-40 are for Todo & Log Service

	@Test
	@Order(20)
	@DisplayName("Create a Todo for existing user")
	void testTodoService_whenATodoIsCreated_returnsNewTodoDetails() {
		todoResponseEntity = rest.postForEntity(getUrl_Todo(), getEntity(todoRequest, true), 
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
	@Order(21)
	@DisplayName("Get all Todo")
	void testTodoService_givenATodoId_returnsTodoDetails() {
		ResponseEntity<List<TestModel_Todo_Response>> r = rest.exchange(getUrl_Todo(), HttpMethod.GET,
				getEntity(null, true), new ParameterizedTypeReference<List<TestModel_Todo_Response>>(){});
		List<TestModel_Todo_Response> todoResponseList = r.getBody();
		
		assertTrue(todoResponseList.size() > 0);
		
		boolean found = false;
		for(TestModel_Todo_Response tr : todoResponseList) {
			if(tr.getId().equals(todoId))
				found = true;
		}
		
		if(!found)
			fail("Todo id #" + todoId + " doesn't exist in the list of all todos");
	}

	
	@Test
	@Order(21)
	@DisplayName("Find all Todos of a user by username")
	void testTodoService_givenAUsername_returnsAllItsTodos() {
		ResponseEntity<List<TestModel_Todo_Response>> r = rest.exchange(mkUrl(getUrl_Todo(),USERNAME), HttpMethod.GET,
				getEntity(todoRequest, true),
				new ParameterizedTypeReference<List<TestModel_Todo_Response>>(){});
		
		List<TestModel_Todo_Response> todoResponseList = r.getBody();
		assertEquals(1, todoResponseList.size());
		assertEquals(todoId, todoResponseList.get(0).getId());
	}
	
	
	@Test
	@Order(30)
	@DisplayName("Create a log for a Todo")
	void testTodoService_givenANewLogForATodo_returnsNewGeneratedLogOfTodo() {
		todoLogRequest.setLog("TODO #" + todoId + " Log - some randome log");
		todoLogResponseEntity = rest.postForEntity(mkUrl(getUrl_Todo(), String.valueOf(todoId), "log"), 
				getEntity(todoLogRequest, true), TestModel_TodoLog_Response.class);
		todoLogResponse = todoLogResponseEntity.getBody();
		todoLogId = todoLogResponse.getId();
		assertEquals(todoLogRequest.getLog(), todoLogResponse.getLog());
	}
	
	
	@Test
	@Order(39)
	@DisplayName("Delete a TodoLog from DB")
	void testTodoService_whenTodoLogIsDeleted_returnsNullOnFindById() {
		ResponseEntity<String> r = rest.exchange(mkUrl(getUrl_Todo(), String.valueOf(todoId), "log", String.valueOf(todoLogId)), 
				HttpMethod.DELETE, getEntity(null, true), String.class);
		assertEquals(HttpStatus.OK, r.getStatusCode());
		
		TodoLog foundTodoLog = todoLogRepository.findById(todoLogId).orElse(null);
		assertNull(foundTodoLog);

		
		//create a new one and it will be deleted with Todo
		testTodoService_givenANewLogForATodo_returnsNewGeneratedLogOfTodo();
	}
	
	
	
	@Test
	@Order(40)
	@DisplayName("Delete a Todo and its Logs from DB")
	void testTodoService_whenTodoIsDeleted_returnsNullOnDBFindByTodoId() {
		rest.exchange(mkUrl(getUrl_Todo(),String.valueOf(todoId),"db"), HttpMethod.DELETE, 
				getEntity(null, true), String.class);
		TodoLog deletedLog = todoLogRepository.findById(todoLogId).orElse(null);
		Todo deletedTodo = todoRepository.findById(todoId).orElse(null);
		assertNull(deletedLog);
		assertNull(deletedTodo);
	}
	
	
	
	//------------------------------- Tests 90-to-100 are for tear down actions

	@Test
	@Order(90)
	@DisplayName("Deactivate a user by username")
	void testUserService_whenAUsernameIsGiven_returnsIsEnabledFalse() {
		ResponseEntity<String> r = rest.exchange(mkUrl(getUrl_User(), USERNAME), HttpMethod.DELETE,
				getEntity(null, false), String.class);

		assertNotNull(r);
		assertEquals("false", r.getBody());
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
		rest.exchange(mkUrl(getUrl_User(), username, "db"), HttpMethod.DELETE, getEntity(null, false), String.class);
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
	private <T> HttpEntity<T> getEntity(T model, boolean newAuth) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		//use new generated user's username & pass for auth, otherwise use admin's
		if(newAuth)
			headers.setBasicAuth(USERNAME, PASSWORD);
		else
			headers.setBasicAuth(env.getProperty("api.auth_user"), env.getProperty("api.auth_pass"));

		return (model != null) ? new HttpEntity<>(model, headers) : new HttpEntity<>(headers);
	}
}
