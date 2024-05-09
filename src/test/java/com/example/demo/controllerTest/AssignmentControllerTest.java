package com.example.demo.controllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.PermitionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.RolesPermitionRepository;
import com.example.demo.repository.UserRepository;

@WebMvcTest
public class AssignmentControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean 
	private AssignmentRepository assignmentRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PermitionRepository permitionRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private RolesPermitionRepository rolespermitionRepository;
	
	
	@Test
	@WithMockUser(roles = "USER")
	public void testAssignmentsPage() throws Exception {
		mockMvc.perform(get("/assignments"))
		.andExpect(status().isOk())
		.andExpect(view().name("assignments"))
		.andExpect(content().string(containsString("Назначения ролей")));
	}
	
	
	@Test
	@WithMockUser(roles = "USER")
	public void testAssignmentsAddPage() throws Exception {
		mockMvc.perform(get("/assignments/add"))
		.andExpect(status().isOk())
		.andExpect(view().name("assignment-new"))
		.andExpect(content().string(containsString("Новое назначение роли")));
	}
	
}









