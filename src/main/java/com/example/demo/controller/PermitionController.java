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
import com.example.demo.repository.PermitionRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/permitions")
public class PermitionController {
	
	@Autowired
	private PermitionRepository permitionRepository;
	
	private Permition permitionFirst;

	
	@GetMapping
	public String permitions(Model model) {
		Iterable<Permition> permitions = permitionRepository.findAllByOrderByPermitionName();
		model.addAttribute("permitions", permitions);
		return "permitions";
	}
	
	
	@GetMapping(value = "/add")
	public String newPermition(Model model) {
		Permition permition = new Permition();
		model.addAttribute("permition", permition);
		return "permition-new";
	}
	
	
	@GetMapping(value = "/edit")
	public String permitionEdit(Model model, @RequestParam Long id) {
		Permition permition = permitionRepository.findById(id).orElseThrow();
		permitionFirst = permition;
		model.addAttribute("permition", permition);
		return "permition-edit";
	}
	

	@PostMapping
	public String deletePermition(@ModelAttribute Permition permition){
		Permition permitionDelete = permitionRepository.findById(permition.getId()).orElseThrow();
		permitionRepository.delete(permitionDelete);
		return "redirect:/permitions";
	}
	
	
	@PostMapping(value = "/add")
	public String saveNewPermition(@Valid @ModelAttribute Permition permition, BindingResult bindingResult){
		
		if(permition.getPermitionName() != null) {
			Permition pr = permitionRepository.findByPermitionName(permition.getPermitionName());
			if(pr != null) {
				bindingResult.addError(new FieldError("permitionName", "permitionName", "Такое право уже существует"));
			}
		}
		
		if(bindingResult.hasErrors()) {
			return "permition-new";
		}
		else {
			permitionRepository.save(permition);
			return "redirect:/permitions";
		}
	}
	
	
	@PostMapping(value = "/edit")
	public String saveEditPermition(@Valid @ModelAttribute Permition permition, BindingResult bindingResult){
		
		if(permition.getPermitionName() != null) {
			Permition pr = permitionRepository.findByPermitionName(permition.getPermitionName());
			if(pr != null && !permitionFirst.getId().equals(pr.getId())) {
				bindingResult.addError(new FieldError("permitionName", "permitionName", "Такое право уже существует"));
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "permition-edit";
		}
		else {
			permitionRepository.save(permition);
			return "redirect:/permitions";
		}
	}
	
}






