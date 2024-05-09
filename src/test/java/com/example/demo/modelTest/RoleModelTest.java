package com.example.demo.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Role;

@SpringBootTest
public class RoleModelTest {

	@InjectMocks
	private Role role;
	
	@BeforeEach
    void setUp() {
    	role = Role.builder()
    			.id(1L)
                .nameRole("Администратор")
                .description("Полные права доступа")
                .build();
    }
	
	
    @Test
    void testRoleId() {
        assertEquals(1L, role.getId());
    }
    
    
    @Test
    void testRoleNameRole() {
        assertEquals("Администратор", role.getNameRole());
    }
    
    
    @Test
    void testRoleDescription() {
        assertEquals("Полные права доступа", role.getDescription());
    }

}
