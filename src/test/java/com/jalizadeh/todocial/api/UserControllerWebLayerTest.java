package com.jalizadeh.todocial.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import com.jalizadeh.todocial.api.models.TestModel_User_Request;
import com.jalizadeh.todocial.api.models.TestModel_User_Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalizadeh.todocial.repository.user.UserRepository;
import com.jalizadeh.todocial.configurations.BasicAuthEntryPoint;
import com.jalizadeh.todocial.service.impl.TokenService;
import com.jalizadeh.todocial.service.impl.UserService;
import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.repository.user.ActivationTokenRepository;
import com.jalizadeh.todocial.service.storage.StorageService;


@Disabled
@WebMvcTest(controllers = UserApi.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerWebLayerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	//@Autowired	//if only @MockBean is used on class level 
	@MockBean
	UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ActivationTokenRepository aTokenRepository;
	
	@MockBean
	private ApplicationEventPublisher eventPublisher;
	
	@MockBean
	private TokenService tokenService;
	
	@MockBean
	private SettingsGeneralConfig settings;
	
	@MockBean
	private DataSource dataSource;
	
	@MockBean
	private BasicAuthEntryPoint basicAuthEntryPoint;
	
	@MockBean
	private StorageService storageService;
	
	@Disabled
	@Test
	void createUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {
		TestModel_User_Request inputUser =
				new TestModel_User_Request("firstname_test", "lastname_test", "username_test", "email@test.com", "12345");
		
		User userDto = new ModelMapper().map(inputUser, User.class);
		userDto.setEnabled(false);
		userDto.setPhoto("default.jpg");
//		when(userService.registerNewUserAccount(any(User.class))).thenReturn(userDto);
		when(userService.sayHi(any(String.class))).thenReturn("");
		
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post("/api/v1/user")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(inputUser));
		
		MvcResult mvcResult = mockMvc.perform(rb).andReturn();
		System.err.println("response: " + mvcResult.getResponse().getContentAsString());
		String responseBodyAsString = mvcResult.getResponse().getContentAsString();
		TestModel_User_Response createdUser = new ObjectMapper().readValue(responseBodyAsString, TestModel_User_Response.class);
		
		assertTrue(createdUser.getId() >= 0);
		assertEquals(inputUser.getFirstname(), createdUser.getFirstname());
		
	}

}
