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

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String passwordRepeat;
	
	@GetMapping
	public String registerForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("passwordRepeat", passwordRepeat);
		return "registration";
	}
	
	
	@PostMapping
	public String processRegistration(@Valid @ModelAttribute User user, BindingResult bindingResult,String passwordRepeat) {
		
		
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
			return "registration";
		}
		else {
		    String encodedPassword = passwordEncoder.encode(user.getPassword());
		    user.setPassword(encodedPassword);
			userRepository.save(user);
			return "redirect:/login";
		}
		
	}

	
}
