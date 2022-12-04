package com.jalizadeh.todocial.web.springbootfirstwebapplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.repository.UserRepository;

@SpringBootTest
class SpringBootFirstWebApplicationTests {
	
	@Autowired
	UserRepository ur;

	@Test
	void findAll() {
		List<User> us = ur.findAll();
		assertNotNull(us);
	}
	
	

}
