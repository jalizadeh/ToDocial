package com.jalizadeh.springboot.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.UserRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class FollowUnfollowController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/users/follow", method=RequestMethod.GET)
	public String follow(@RequestParam String target) {
		
		User user = userService.GetAuthenticatedUser();
		User targetUser = userRepository.findByUsername(target);
		user.getFollowings().add(targetUser);
		userRepository.save(user);
		
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value="/users/unfollow", method=RequestMethod.GET)
	public String unfollow(@RequestParam String target) {
		
		User user = userService.GetAuthenticatedUser();
		user.getFollowings().removeIf(f -> f.getUsername().equals(target));
		userRepository.save(user);
		
		return "redirect:/@" + user.getUsername();
	}
	
}
