package com.jalizadeh.todocial.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.VerificationToken;
import com.jalizadeh.todocial.web.repository.VerificationTokenRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTests {
	
	private final String baseUrl = "http://localhost";
	private final String servicePath = "/user";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VerificationTokenRepository vtRepository;
	
	@Autowired
	Environment env;
	
	@Autowired
	TestRestTemplate rest;
	
	private UserTestInput inputUser = new UserTestInput("firstname_test", "lastname_test", "username_test", "email@test.com", "12345");
	private UserTestResponse responseUser;
	private Long userId;
	private String username;
	private ResponseEntity<UserTestResponse> response;

	
	@BeforeAll
	void setup() {
		deleteUser(inputUser.getUsername());
		
		// create a new test user
		response = rest.postForEntity(getUrl(), getEntity(true), UserTestResponse.class);
		assertNotNull(response);
		responseUser = response.getBody();
		assertEquals(inputUser.getFirstname(), responseUser.getFirstname());

		this.userId = responseUser.getId();
		this.username = responseUser.getUsername();
		System.err.println("User " + inputUser.getFirstname() + " created successfully");
	}
	
	@Test
	@Order(1)
	@Tag("positive")
	@DisplayName("Created user & exists in the DB")
	void createUser() {
		User dbUser = userRepository.findById(userId).get();
		assertEquals(dbUser.getFirstname(), responseUser.getFirstname());
		assertEquals(dbUser.getLastname(), responseUser.getLastname());
		assertEquals(dbUser.getEmail(), responseUser.getEmail());
		assertEquals(dbUser.getPhoto(), "default.jpg");
		assertFalse(dbUser.isEnabled());
	}
	
	@Test
	@Order(2)
	@Tag("positive")
	@DisplayName("Activated user after registration")
	void activateUser() {
		//check if activation code exists
		VerificationToken vToken = vtRepository.findByUserId(userId);
		assertNotNull(vToken, "Verification token doesnt exist");
		
		//get activation code
		ResponseEntity<TestToken> resp1 = 
				rest.exchange(mkUrl(getUrl(), username, "activation_token"), HttpMethod.GET, getEntity(false), TestToken.class);
		assertEquals(vToken.getToken(), resp1.getBody().getToken());
		
		
		//activate user
		ResponseEntity<String> resp2 = 
				rest.postForEntity(mkUrl(getUrl(), username, "activate?token=" + vToken.getToken()), getEntity(false), String.class);
		assertEquals("valid", resp2.getBody());
		
		//activation code should be removed
		assertNull(vtRepository.findByUserId(userId), "Verification token is not deleted after activation");
	}
	
	
	@AfterAll
	void teardown() {
		deleteUser(username);
		System.err.println("User " + inputUser.getFirstname() + " deleted successfully");
	}
	
	
	
	private void deleteUser(String username) {
		rest.exchange(mkUrl(getUrl(), username, "db"), 
				HttpMethod.DELETE, getEntity(false), String.class);
		assertNull(userRepository.findByUsername(username));
	}

	private String mkUrl(String... list) {
		return Arrays.asList(list).stream().collect(Collectors.joining("/"));
	}

	private String getUrl() {
		return this.baseUrl + ":" + this.port + env.getProperty("api.rootUrl") + servicePath;
	}
	
	
	private HttpEntity<UserTestInput> getEntity(boolean body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth(env.getProperty("api.auth_user"), env.getProperty("api.auth_pass"));
		
		return body ? new HttpEntity<>(inputUser, headers) : new HttpEntity<>(headers); 
	}
	

}
