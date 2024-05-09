package com.example.demo.securityTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.example.demo.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

	@Autowired 
	private WebApplicationContext context;
	
	@Autowired 
	private MockMvc mockMvcAnonymous;
	
	@Autowired 
	private MockMvc mockMvcUser;
	
	@MockBean
	private UserRepository userRepository;

	
	@BeforeEach()
	public void setup(){
		
		mockMvcUser = MockMvcBuilders
	    		.webAppContextSetup(context)
	    		.build();
	    
		mockMvcAnonymous = MockMvcBuilders
	            .webAppContextSetup(context)
	            .apply(springSecurity())  
	            .build();
	}
   
	
	//----->>>>>>>>>> Tests for anonymous user <<<<<<<<<<-----
    @Test
    @DisplayName("JUnit test for anonymous access to home page")
    void givenUserIsAnonymous_whenGetHomePage_thenOk() throws Exception {
    	mockMvcAnonymous.perform(get("/"))
    		.andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("JUnit test for anonymous access to login page")
    void givenUserIsAnonymous_whenGetLoginPage_thenOk() throws Exception {
    	mockMvcAnonymous.perform(get("/login"))
    		.andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("JUnit test for anonymous access to register page")
    void givenUserIsAnonymous_whenGetRegisterPage_thenOk() throws Exception {
    	mockMvcAnonymous.perform(get("/register"))
    		.andExpect(status().isOk());
    }
    
    
    @Test
    @DisplayName("JUnit test for redirection when anonymous user accesses users page")
    public void givenUserIsAnonymous_whenGetUsersPage_thenRedirection() throws Exception{
    	mockMvcAnonymous.perform(get("/users"))
    		.andExpect(status().is3xxRedirection());
    }
    
    
    @Test
    @DisplayName("JUnit test for redirection when anonymous user accesses roles page")
    public void givenUserIsAnonymous_whenGetRolesPage_thenRedirection() throws Exception {
        mockMvcAnonymous.perform(get("/roles"))
            .andExpect(status().is3xxRedirection());
    }
    
    
	@DisplayName("JUnit test for redirection when anonymous user accesses permitions page")
    @Test
    public void givenUserIsAnonymous_whenGetPermitionsPage_thenRedirection() throws Exception {
        mockMvcAnonymous.perform(get("/permitions"))
            .andExpect(status().is3xxRedirection());
    }
    
    
    @Test
    @DisplayName("JUnit test for redirection when anonymous user accesses assignments page")
    public void givenUserIsAnonymous_whenGetAssignmentsPage_thenRedirection() throws Exception {
        mockMvcAnonymous.perform(get("/assignments"))
            .andExpect(status().is3xxRedirection());
    }
    
    
    @Test
    @DisplayName("JUnit test for redirection when anonymous user accesses rolespermissions page")
    public void givenUserIsAnonymous_whenGetRolesPermissionsPage_thenRedirection() throws Exception {
        mockMvcAnonymous.perform(get("/rolespermitions"))
            .andExpect(status().is3xxRedirection());
    }
    
   
    //----->>>>>>>>>> Tests for authorized user <<<<<<<<<<-----
    @Test
    @DisplayName("JUnit test for authorized user accessing users page")
    @WithMockUser(roles = "USER")
    void givenUserIsAuthorized_whenGetUsersPage_thenOk() throws Exception {
        mockMvcUser.perform(get("/users"))
                .andExpect(status().isOk());
    }

    
    @Test
    @DisplayName("JUnit test for authorized user accessing roles page")
    @WithMockUser(roles = "USER")
    void givenUserIsAuthorized_whenGetRolesPage_thenOk() throws Exception {
        mockMvcUser.perform(get("/roles"))
                .andExpect(status().isOk());
    }

    
    @Test
    @DisplayName("JUnit test for authorized user accessing permissions page")
    @WithMockUser(roles = "USER")
    void givenUserIsAuthorized_whenGetPermissionsPage_thenOk() throws Exception {
        mockMvcUser.perform(get("/permitions"))
                .andExpect(status().isOk());
    }

    
    @Test
    @DisplayName("JUnit test for authorized user accessing assignments page")
    @WithMockUser(roles = "USER")
    void givenUserIsAuthorized_whenGetAssignmentsPage_thenOk() throws Exception {
        mockMvcUser.perform(get("/assignments"))
                .andExpect(status().isOk());
    }

    
    @Test
    @DisplayName("JUnit test for authorized user accessing rolespermissions page")
    @WithMockUser(roles = "USER")
    void givenUserIsAuthorized_whenGetRolesPermissionsPage_thenOk() throws Exception {
        mockMvcUser.perform(get("/rolespermitions"))
                .andExpect(status().isOk());
    }
      
    
}
















