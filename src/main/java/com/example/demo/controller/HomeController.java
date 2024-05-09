package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping
	public String home(Model model) {
		
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		
		if(username != null) {
			User user =  userRepository.findByUsername(username);
			model.addAttribute("user", user);
			model.addAttribute("username", username);
		}
		
		return "home";
	}
	
}


