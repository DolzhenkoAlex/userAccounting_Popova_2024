package com.example.demo.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.User;

@SpringBootTest
public class UserModelTest {

	@InjectMocks
    private User user;
	
    @BeforeEach
    void setUp() {
    	user = User.builder()
    			.id(1L)
                .username("Анна")
                .password("12345")
                .email("Anna@gmail.com")
                .status("Активен")
                .build();
    }
    
    
    @Test
    void testUsertId() {
        assertEquals(1L, user.getId());
    }
    
    
    @Test
    void testUsertUsername() {
        assertEquals("Анна", user.getUsername());
    }
    
    
    @Test
    void testUsertPassword() {
        assertEquals("12345", user.getPassword());
    }
    
    
    @Test
    void testUsertEmail() {
        assertEquals("Anna@gmail.com", user.getEmail());
    }
    
    
    @Test
    void testUsertStatus() {
        assertEquals("Активен", user.getStatus());
    }
    
}
