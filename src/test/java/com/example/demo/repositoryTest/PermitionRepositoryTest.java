package com.example.demo.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Permition;
import com.example.demo.repository.PermitionRepository;

@DataJpaTest
public class PermitionRepositoryTest {
	
	@Autowired 
	private PermitionRepository permitionRepository;
	
	@InjectMocks
	private Permition permition;
	
	private Date date = new Date(1615994763000L);
	
    @BeforeEach
    void setUp() {
    	
    	permition = Permition.builder()
    			.permitionName("Редактирование статьи")
    			.description("Возможность редактировать статью")
    			.dateCreate(date)
                .build();
    }
    
    
    @Test
    public void givenPermitionObject_whenSave_thenReturnSavedPermition(){
    	
    	permition = Permition.builder()
    			.permitionName("Редактирование статьи")
    			.description("Возможность редактировать статью")
    			.dateCreate(date)
                .build();

    	Permition savedPermition = permitionRepository.save(permition);
        assertThat(savedPermition).isNotNull();
        assertThat(savedPermition.getId()).isGreaterThan(0);
        
    }
    
    
    @Test
    public void givenPermitionList_whenFindAll_thenPermitionsList(){
    	
    	Date date = new Date(1615994763111L);

    	Permition permition1 = Permition.builder()
    			.permitionName("Удаление статьи")
    			.description("Возможность удаление статьи")
    			.dateCreate(date)
                .build();
    	
    	Integer listSize = permitionRepository.findAll().size();
    	
    	permitionRepository.save(permition);
    	permitionRepository.save(permition1);

        List<Permition> permitionList = permitionRepository.findAll();

        assertThat(permitionList).isNotNull();
        assertThat(permitionList.size()).isEqualTo(listSize + 2);
    }
    
    
    @Test
    public void givenPermitionObject_whenFindById_thenReturnPermitionObject(){

    	permitionRepository.save(permition);
    	
    	Permition permitionDB = permitionRepository.findById(permition.getId()).get();
    	
        assertThat(permitionDB).isNotNull();
    }
    
    
    @Test
    public void givenPermitionObject_whenUpdatePermition_thenReturnUpdatedPermition(){

    	permitionRepository.save(permition);
    	Date date = new Date(1615994763111L);

    	Permition savedPermition = permitionRepository.findById(permition.getId()).get();
    	savedPermition.setId(1L);
    	savedPermition.setDateCreate(date);
    	savedPermition.setPermitionName("Удаление статьи");
    	savedPermition.setDescription("Возможность удалять статью");
   
    	Permition updatedPermition =  permitionRepository.save(savedPermition);

        assertThat(updatedPermition.getId()).isEqualTo(1L);
        assertThat(updatedPermition.getDateCreate()).isEqualTo(date);
        assertThat(updatedPermition.getPermitionName()).isEqualTo("Удаление статьи");
        assertThat(updatedPermition.getDescription()).isEqualTo("Возможность удалять статью");
    }
    
    
    @Test
    public void givenPermitionObject_whenDelete_thenRemovePermition(){

    	permitionRepository.save(permition);

    	permitionRepository.deleteById(permition.getId());
        Optional<Permition> permitionOptional = permitionRepository.findById(permition.getId());

        assertThat(permitionOptional).isEmpty();
    }
    
}








