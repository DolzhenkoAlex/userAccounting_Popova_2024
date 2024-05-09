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

import com.example.demo.model.Role;
import com.example.demo.model.RolesPermition;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.RolesPermitionRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RolesPermitionRepository rolespermitionRepository;
	
	private Role roleFirst;
	
	
	@GetMapping
	public String roles(Model model) {
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<RolesPermition> rolesPermitions = rolespermitionRepository.findAll();
		
		model.addAttribute("roles", roles);
		model.addAttribute("rolesPermitions", rolesPermitions);
		return "roles";
	}
	
	
	@GetMapping(value = "/add")
	public String newRole(Model model) {
		Role role = new Role();
		model.addAttribute("role", role);
		return "role-new";
	}
	
	
	@GetMapping(value = "/edit")
	public String roleEdit(Model model, @RequestParam Long id) {
		Role role = roleRepository.findById(id).orElseThrow();
		roleFirst = role;
		model.addAttribute("role", role);
		return "role-edit";
	}
	
	
	@PostMapping
	public String deleteRole(@ModelAttribute Role role){
		Role roleDelete = roleRepository.findById(role.getId()).orElseThrow();
		Iterable<RolesPermition> rolesPermitions = rolespermitionRepository.findAllByRoleId(roleDelete.getId());
		
		for (RolesPermition rp : rolesPermitions) {
		    rolespermitionRepository.delete(rp);
		}
		
		roleRepository.delete(roleDelete);
		return "redirect:/roles";
	}
	
	
	@PostMapping(value = "/add")
	public String saveNewRole(@Valid @ModelAttribute Role role, BindingResult bindingResult){
		
		if(role.getNameRole() != null) {
			Role rl = roleRepository.findByNameRole(role.getNameRole());
			if(rl != null) {
				bindingResult.addError(new FieldError("nameRole", "nameRole", "Такая роль уже существует"));
			}
		}
		
		
		if(bindingResult.hasErrors()) {
			return "role-new";
		}
		else {
			roleRepository.save(role);
			return "redirect:/roles";
		}
	}
	
	
	@PostMapping(value = "/edit")
	public String saveEditRole(@Valid @ModelAttribute Role role, BindingResult bindingResult){
		
		if(role.getNameRole() != null) {
			Role rl = roleRepository.findByNameRole(role.getNameRole());
			if(rl != null && !roleFirst.getId().equals(rl.getId())) {
				bindingResult.addError(new FieldError("nameRole", "nameRole", "Такая роль уже существует"));
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "role-edit";
		}
		else {
			roleRepository.save(role);
			return "redirect:/roles";
		}
	}
	
}













