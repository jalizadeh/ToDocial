package com.jalizadeh.todocial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {

	@GetMapping("/diary")
	public String showDiary() {
		return "diary/diary";
	}
	
}
