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

import com.example.demo.model.Permition;
import com.example.demo.model.Role;
import com.example.demo.model.RolesPermition;
import com.example.demo.repository.PermitionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.RolesPermitionRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/rolespermitions")
public class RolesPermitionController {

	@Autowired
	private RolesPermitionRepository rolespermitionRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermitionRepository permitionRepository;
	
	private RolesPermition rpFirst;

	
	@GetMapping
	public String rolespermitions(Model model) {
		Iterable<RolesPermition> rolespermitions = rolespermitionRepository.findAllByOrderByRoleNameAndPermition();
		model.addAttribute("rolespermitions", rolespermitions);
		return "rolespermitions";
	}
	
	
	@GetMapping(value = "/add")
	public String newRolesPermitions(Model model) {
		
		RolesPermition rolespermition = new RolesPermition();
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<Permition> permitions = permitionRepository.findAllByOrderByPermitionName();
		
		model.addAttribute("rolesPermition", rolespermition);
		model.addAttribute("roles", roles);
		model.addAttribute("permitions", permitions);
		
		return "rolespermition-new";
	}
	
	
	@GetMapping(value = "/edit")
	public String rolespermitionEdit(Model model, @RequestParam Long id) {
		RolesPermition rolespermition = rolespermitionRepository.findById(id).orElseThrow();
		Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
		Iterable<Permition> permitions = permitionRepository.findAllByOrderByPermitionName();
		rpFirst = rolespermition;
		
		model.addAttribute("rolesPermition", rolespermition);
		model.addAttribute("roles", roles);
		model.addAttribute("permitions", permitions);
		
		return "rolespermition-edit";
	}
	

	@PostMapping
	public String deleteRolespermition(@ModelAttribute RolesPermition rolespermition){
		RolesPermition rolespermitionDelete = rolespermitionRepository.findById(rolespermition.getId()).orElseThrow();
		rolespermitionRepository.delete(rolespermitionDelete);
		return "redirect:/rolespermitions";
	}
	
	
	@PostMapping(value = "/add")
	public String saveNewRolespermition(@Valid @ModelAttribute RolesPermition rolesPermition, BindingResult bindingResult, Model model){
		

		if(rolesPermition.getRole() != null && rolesPermition.getPermition() != null) {
			Long id_role =  rolesPermition.getRole().getId();
			Long id_permition =  rolesPermition.getPermition().getId();	
			RolesPermition rp = rolespermitionRepository.findByPermitionIdAndRoleId(id_permition, id_role);
			
			if(rp != null) {
				bindingResult.addError(new FieldError("permition", "permition", "Такое право для роли уже существует"));
			}
		}
		
		if(bindingResult.hasErrors()) {
			
			Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
			Iterable<Permition> permitions = permitionRepository.findAllByOrderByPermitionName();
			
			model.addAttribute("roles", roles);
			model.addAttribute("permitions", permitions);
			
			return "rolespermition-new";
		}
		else {
			rolespermitionRepository.save(rolesPermition);
			return "redirect:/rolespermitions";
		}
		
		
	}
	
	
	@PostMapping(value = "/edit")
	public String saveEditPermition(@Valid @ModelAttribute RolesPermition rolespermition, BindingResult bindingResult,  Model model){
		
		if(rolespermition.getRole() != null && rolespermition.getPermition() != null) {
			
			Long id_role =  rolespermition.getRole().getId();
			Long id_permition =  rolespermition.getPermition().getId();	
			RolesPermition rp = rolespermitionRepository.findByPermitionIdAndRoleId(id_permition, id_role);
			
			if(rp != null && rp.getId() != rpFirst.getId()) {
				bindingResult.addError(new FieldError("permition", "permition", "Такое право для роли уже существует"));	
			}
		}
		
		if (bindingResult.hasErrors()) {
			
			Iterable<Role> roles = roleRepository.findAllByOrderByNameRole();
			Iterable<Permition> permitions = permitionRepository.findAllByOrderByPermitionName();
			
			model.addAttribute("roles", roles);
			model.addAttribute("permitions", permitions);
			
			return "rolespermition-edit";
		}
		else {
			rolespermitionRepository.save(rolespermition);
			return "redirect:/rolespermitions";
		}
	}
	
	
}

