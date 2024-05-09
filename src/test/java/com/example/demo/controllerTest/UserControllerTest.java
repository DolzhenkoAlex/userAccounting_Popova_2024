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
public class UserControllerTest {
	
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
	public void testUsersPermitionPage() throws Exception {
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(view().name("users"))
			.andExpect(content().string(containsString("Пользователи")));
	}
	
	
	@Test
	@WithMockUser(roles = "USER")
	public void testUserAddPage() throws Exception {
		mockMvc.perform(get("/users/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("user-new"))
			.andExpect(content().string(containsString("Новый пользователь")));
	}

}
