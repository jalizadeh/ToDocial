package com.jalizadeh.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.springboot.web.model.Test;
import com.jalizadeh.springboot.web.repository.TestRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class TestController {

	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingsGeneralConfig settings;
	
	@GetMapping("/test-start")
	public String startTest(ModelMap model) {
		Test test = new Test();
		test.setUser(userService.GetAuthenticatedUser());
		Test savedTest = testRepository.save(test);
		return "redirect:/test?uid=" + savedTest.getUid() + "&tid=1";
	}
	
	@GetMapping("/test")
	public String doTest(ModelMap m, 
			@RequestParam(required=false) String uid, @RequestParam(required=false) String tid) {
		
		if(uid == null || tid == null) {
			List<Test> allTests = testRepository.findAllByUserId(userService.GetAuthenticatedUser().getId());
			m.put("settings", settings);
			m.put("PageTitle", "Personal Test");
			m.put("allTests", allTests);
			return "test/start"; 
		}
		
		Test test = testRepository.findAllByUid(uid);
		if(test == null) return "test/start";
		
		switch (tid) {
			case "1": m.put("test", test); m.put("tid", 1); return "test/test";
			case "2": m.put("test", test); m.put("tid", 2); return "test/test";
			case "3": m.put("test", test); m.put("tid", 3); return "test/test";
			case "4": m.put("test", test); m.put("tid", 4); return "test/test";
			case "5": m.put("test", test); m.put("tid", 5); return "test/test";
			case "6": m.put("test", test); m.put("tid", 6); return "test/test";
			case "7": m.put("test", test); m.put("tid", 7); return "test/test";
			default : return "/";
		}			
	}

	
	
	@PostMapping("/test")
	public String getTest(ModelMap model,
			@RequestParam String uid, @RequestParam String tid,
			@RequestParam Long q1, @RequestParam Long q2,
			@RequestParam Long q3, @RequestParam Long q4,
			@RequestParam Long q5, @RequestParam Long q6,
			@RequestParam Long q7, @RequestParam Long q8,
			@RequestParam Long q9, @RequestParam Long q10) {
		
		Test test = testRepository.findAllByUid(uid);
		
		if (test == null) return "error";
		
		switch (tid) {
			case "1":
				test.setBody_health(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test?uid="+test.getUid() + "&tid=2";
			
			case "2":
				test.setMental_health(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test?uid="+test.getUid() + "&tid=3";
				
			case "3":
				test.setFinancial(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test?uid="+test.getUid() + "&tid=4";
				
			case "4":
				test.setBusiness(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test?uid="+test.getUid() + "&tid=5";
			
			case "5":
				test.setLife_style(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test?uid="+test.getUid() + "&tid=6";
			
			case "6":
				test.setSpiritual(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test?uid="+test.getUid() + "&tid=7";
			
			//family and relationship have one set of questions, but are rated as two columns in charts and DB
			case "7":			
				test.setFamily(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				test.setRelationship(q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10);
				testRepository.save(test);
				return "redirect:/test";
				
			default: return "error";
		}
	}
	
}
