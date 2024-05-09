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
public class RoleControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean 
	private AssignmentRepository assignmentRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@MockBean
	private RolesPermitionRepository rolesPermitionRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PermitionRepository permitionRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	
	@Test
	@WithMockUser(roles = "USER")
	public void testRolePage() throws Exception {
		mockMvc.perform(get("/roles"))
			.andExpect(status().isOk())
			.andExpect(view().name("roles"))
			.andExpect(content().string(containsString("Роли")));
	}
	
	
	@Test
	@WithMockUser(roles = "USER")
	public void testRoleAddPage() throws Exception {
		mockMvc.perform(get("/roles/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("role-new"))
			.andExpect(content().string(containsString("Новая роль")));
	}

}
