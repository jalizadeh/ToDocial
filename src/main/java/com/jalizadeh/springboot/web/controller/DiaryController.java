package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaryController {

	@GetMapping("/diary")
	public String ShowDiary() {
		return "diary/diary";
	}
}
