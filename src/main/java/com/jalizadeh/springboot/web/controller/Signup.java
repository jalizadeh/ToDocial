package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Signup {

	@RequestMapping("/signup")
	public String SignupMessage() {
		return "signup";
	}
}
