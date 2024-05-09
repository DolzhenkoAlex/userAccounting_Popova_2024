package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {

	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private Assignment assignmentFirst;

	
	@GetMapping
	public String assignments(Model model) {
		Iterable<Assignment> assignments = assignmentRepository.findAllByOrderByUser();
		model.addAttribute("assignments", assignments);
		return "assignments";
	}
	
	
	@GetMapping(value = "/add")
	public String newAssignment(Model model) {
		Assignment assignment = new Assignment();
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<User> users = userRepository.findAllByOrderByUsername();
		
		model.addAttribute("roles", roles);
		model.addAttribute("users", users);
		model.addAttribute("assignment", assignment);
		return "assignment-new";
	}
	
	
	@GetMapping(value = "/edit")
	public String assignmentEdit(Model model, @RequestParam Long id) {
		Assignment assignment = assignmentRepository.findById(id).orElseThrow();
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<User> users = userRepository.findAllByOrderByUsername();
		assignmentFirst = assignment;
		
		model.addAttribute("roles", roles);
		model.addAttribute("users", users);
		model.addAttribute("assignment", assignment);
		return "assignment-edit";
	}
	
	
	@PostMapping
	public String deleteAssignment(@ModelAttribute Assignment assignment){
		Assignment assignmentDelete = assignmentRepository.findById(assignment.getId()).orElseThrow();
		assignmentRepository.delete(assignmentDelete);
		return "redirect:/assignments";
	}
	
	
	@PostMapping(value = "/add")
	public String saveNewAssignment(@Valid @ModelAttribute Assignment assignment, BindingResult bindingResult, Model model){
		
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<User> users = userRepository.findAllByOrderByUsername();
		model.addAttribute("roles", roles);
		model.addAttribute("users", users);
		
		if(assignment.getRole() != null && assignment.getUser() != null) {
			Long id_role = assignment.getRole().getId();
			Long id_user = assignment.getUser().getId();
			Assignment as = assignmentRepository.findByUserIdAndRoleId(id_user, id_role);
			if(as != null) {
				bindingResult.addError(new FieldError("role", "role", "Такая роль для пользователя уже существует"));
			}
		}
		
		if(bindingResult.hasErrors()) {
			return "assignment-new";
		}
		else {
			assignmentRepository.save(assignment);
			return "redirect:/assignments";
		}
	}
	
	
	@PostMapping(value = "/edit")
	public String saveEditAssignment(@Valid @ModelAttribute Assignment assignment, BindingResult bindingResult, Model model){
		
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<User> users = userRepository.findAllByOrderByUsername();
		model.addAttribute("roles", roles);
		model.addAttribute("users", users);
		
		if(assignment.getRole() != null && assignment.getUser() != null) {
			Long id_role = assignment.getRole().getId();
			Long id_user = assignment.getUser().getId();
			Assignment as = assignmentRepository.findByUserIdAndRoleId(id_user, id_role);
			if(as != null && as.getId() != assignmentFirst.getId()) {
				bindingResult.addError(new FieldError("role", "role", "Такая роль для пользователя уже существует"));
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "assignment-edit";
		}
		else {
			assignmentRepository.save(assignment);
			return "redirect:/assignments";
		}
	}
	
	
}






