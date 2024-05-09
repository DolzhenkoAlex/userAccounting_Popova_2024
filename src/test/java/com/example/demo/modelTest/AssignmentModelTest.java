package com.example.demo.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Assignment;
import com.example.demo.model.Role;
import com.example.demo.model.User;

@SpringBootTest
public class AssignmentModelTest {

	@InjectMocks
    private Assignment assignment;
	
	private User user;
	private Role role;
	private Date date = new Date(1615994763000L);
	
    @BeforeEach
    void setUp() {
    	
    	user = User.builder().build();
    	role = Role.builder().build();
    	
    	assignment = Assignment.builder()
    			.id(1L)
    			.dateCreate(date)
    			.user(user)
    			.role(role)
                .build();
    }
    
  
    @Test
    void testAssignmentId() {
        assertEquals(1L, assignment.getId());
    }
    
    
    @Test
    void testAssignmentDateCreate() {
        assertEquals(date, assignment.getDateCreate());
    }
    
    
    @Test
    void testAssignmentUser() {
        assertEquals(user, assignment.getUser());
    }
    
    
    @Test
    void testAssignmentRole() {
        assertEquals(role, assignment.getRole());
    }
    
}










