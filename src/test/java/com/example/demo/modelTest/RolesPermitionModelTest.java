package com.example.demo.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Permition;
import com.example.demo.model.Role;
import com.example.demo.model.RolesPermition;

@SpringBootTest
public class RolesPermitionModelTest {

	@InjectMocks
    private RolesPermition rolesPermition;
	
	private Permition permition;
	private Role role;
	private Date date = new Date(1615994763000L);
	
    @BeforeEach
    void setUp() {

    	rolesPermition = RolesPermition.builder()
    			.id(1L)
    			.role(role)
    			.permition(permition)
    			.dateCreate(date)
                .build();
    }
    
    
    @Test
    void testRolesPermitionId() {
        assertEquals(1L, rolesPermition.getId());
    }
    
    
    @Test
    void testRolesPermitionRole() {
        assertEquals(role, rolesPermition.getRole());
    }
    
    
    @Test
    void testRolesPermitionPermition() {
        assertEquals(permition, rolesPermition.getPermition());
    }
    
    
    @Test
    void testRolesPermitionDateCreate() {
        assertEquals(date, rolesPermition.getDateCreate());
    }
    
}
