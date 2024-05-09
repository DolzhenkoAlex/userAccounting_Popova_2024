package com.example.demo.modelTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Permition;

@SpringBootTest
public class PermitionModelTest {

	@InjectMocks
    private Permition permition;
	
	private Date date = new Date(1615994763000L);
	
    @BeforeEach
    void setUp() {
    	
    	permition = Permition.builder()
    			.id(1L)
    			.permitionName("Редактирование статьи")
    			.description("Возможность редактировать статью")
    			.dateCreate(date)
                .build();
    }
    
    
    @Test
    void testPermitionId() {
        assertEquals(1L, permition.getId());
    }
    
    
    @Test
    void testPermitionPermitionName() {
        assertEquals("Редактирование статьи", permition.getPermitionName());
    }
    
    
    @Test
    void testPermitionDescription() {
        assertEquals("Возможность редактировать статью", permition.getDescription());
    }
    
    
    @Test
    void testPermitionDate() {
        assertEquals(date, permition.getDateCreate());
    }
    
}
