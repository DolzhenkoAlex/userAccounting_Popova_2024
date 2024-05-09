package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Assignment;
import com.example.demo.model.User;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String passwordRepeat;
	private String usernameFirst;
	private String emailFirst;
	private String passwordFirst;
	
	
	@GetMapping
	public String users(Model model) {
		Iterable<User> users = userRepository.findAllByOrderByUsername();
		Iterable<Assignment> assignments = assignmentRepository.findAll();
		
		model.addAttribute("assignments", assignments);
		model.addAttribute("users", users);
		return "users";
	}
	
	
	@GetMapping(value = "/add")
	public String newUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("passwordRepeat", passwordRepeat);
		return "user-new";
	}
	
	
	@GetMapping(value = "/edit")
	public String userEdit(Model model, @RequestParam Long id) {
		User user = userRepository.findById(id).orElseThrow();
		usernameFirst = user.getUsername();
		emailFirst = user.getEmail();
		passwordFirst = user.getPassword();
		model.addAttribute("user", user);
		model.addAttribute("passwordRepeat", passwordRepeat);
		return "user-edit";
	}
	
	
	@PostMapping
	public String deleteUser(@ModelAttribute User user){
		User userDelete = userRepository.findById(user.getId()).orElseThrow();
		
		Iterable<Assignment> assignments = assignmentRepository.findAllByUserId(userDelete.getId());
		for(Assignment as : assignments) {
			assignmentRepository.delete(as);
		}
		
		userRepository.delete(userDelete);
		return "redirect:/users";
	}
	
	
	
	@PostMapping(value = "/add")
	public String saveNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult,String passwordRepeat){
		
		if(userRepository.findByUsername(user.getUsername()) != null) {
			bindingResult.addError(new FieldError("username", "username", "Выберите другое имя пользователя"));
		}

		if(userRepository.findByEmail(user.getEmail()) != null) {
			bindingResult.addError(new FieldError("email", "email", "Данная почта уже используеться"));
		}
		
		if(!user.getPassword().equals(passwordRepeat)) {
			bindingResult.addError(new FieldError("password", "password", "Повторите введенный пароль"));
		}
		
		if(bindingResult.hasErrors()) {
			return "user-new";
		}
		else {
		    String encodedPassword = passwordEncoder.encode(user.getPassword());
		    user.setPassword(encodedPassword);
			userRepository.save(user);
			return "redirect:/users";
		}
		
	}
	
	
	@PostMapping(value = "/edit")
	public String saveEditUser(@Valid @ModelAttribute User user, BindingResult bindingResult, String passwordRepeat){
		
		if(userRepository.findByUsername(user.getUsername()) != null 
				&& !userRepository.findByUsername(user.getUsername()).getUsername().equals(usernameFirst)) {
			bindingResult.addError(new FieldError("username", "username", "Выберите другое имя пользователя"));
		}

		if(userRepository.findByEmail(user.getEmail()) != null 
				&& !userRepository.findByEmail(user.getEmail()).getEmail().equals(emailFirst)) {
			bindingResult.addError(new FieldError("email", "email", "Данная почта уже используеться"));
		}
	
		if(!user.getPassword().equals(passwordRepeat) && !user.getPassword().equals(passwordFirst)) {
			bindingResult.addError(new FieldError("password", "password", "Повторите введенный пароль"));
		}
		
		if (bindingResult.hasErrors()) {
			return "user-edit";
		}
		else {
			if(!user.getPassword().equals(passwordFirst)) {
			    String encodedPassword = passwordEncoder.encode(user.getPassword());
			    user.setPassword(encodedPassword);
			}
			userRepository.save(user);
			return "redirect:/users";
		}
	}
	
	
}
