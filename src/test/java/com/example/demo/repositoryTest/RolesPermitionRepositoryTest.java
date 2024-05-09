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
import com.example.demo.model.Role;
import com.example.demo.model.RolesPermition;
import com.example.demo.repository.PermitionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.RolesPermitionRepository;

@DataJpaTest
public class RolesPermitionRepositoryTest {
	
	@Autowired 
	private RolesPermitionRepository rolesPermitionRepository;
	
	@Autowired 
	private PermitionRepository permitionRepository;
	
	@Autowired 
	private RoleRepository roleRepository;
  
	@InjectMocks
	private RolesPermition rolesPermition;
  
	private Role role;
	private Permition permition;
	private Date date = new Date(1615994763000L);
  
	@BeforeEach
	void setUp() {
		
    	permition = Permition.builder()
    			.permitionName("Редактирование статьи")
    			.description("Возможность редактировать статью")
    			.dateCreate(date)
                .build();

		role = Role.builder()
				.nameRole("Администратор")
				.description("Полные права доступа")
				.build();
		
    	permitionRepository.save(permition);
    	roleRepository.save(role);
      
		rolesPermition = RolesPermition.builder()
				.role(role)
				.permition(permition)
				.dateCreate(date)
				.build();
	}
  
  
	@Test
	public void givenRolesPermitionObject_whenSave_thenReturnSavedRolesPermition(){
		
		rolesPermition = RolesPermition.builder()
				.role(role)
				.permition(permition)
				.dateCreate(date)
				.build();

		RolesPermition savedRolesPermition = rolesPermitionRepository.save(rolesPermition);
		assertThat(savedRolesPermition).isNotNull();
		assertThat(savedRolesPermition.getId()).isGreaterThan(0);
      
  }
  
  
	@Test
 	public void givenRolesPermitionList_whenFindAll_thenReturnRolesPermitionList(){
         
		RolesPermition rolesPermition1 = RolesPermition.builder()
				.role(role)
				.permition(permition)
				.dateCreate(date)
				.build();
         
		Integer listSize = rolesPermitionRepository.findAll().size();
         
		rolesPermitionRepository.save(rolesPermition);
		rolesPermitionRepository.save(rolesPermition1);

		List<RolesPermition> rolesPermitionList = rolesPermitionRepository.findAll();

		assertThat(rolesPermitionList).isNotNull();
		assertThat(rolesPermitionList.size()).isEqualTo(listSize + 2);
	}
  
  
    @Test
    public void givenRolesPermitionObject_whenFindById_thenReturnRolesPermitionObject(){

    	rolesPermitionRepository.save(rolesPermition);
     
    	RolesPermition rolesPermitionDB = rolesPermitionRepository.findById(rolesPermition.getId()).get();
     
    	assertThat(rolesPermitionDB).isNotNull();
    }
  
  
    @Test
    public void givenRolesPermitionObject_whenUpdateRolesPermition_thenReturnUpdatedRolesPermition(){
    	
    	rolesPermitionRepository.save(rolesPermition);
    	Date date = new Date(1615994763111L);

    	RolesPermition savedRolesPermition = rolesPermitionRepository.findById(rolesPermition.getId()).get();
    	savedRolesPermition.setId(1L);
    	savedRolesPermition.setRole(role);
    	savedRolesPermition.setPermition(permition);
    	savedRolesPermition.setDateCreate(date);

    	RolesPermition updatedRolesPermition =  rolesPermitionRepository.save(savedRolesPermition);

    	assertThat(updatedRolesPermition.getId()).isEqualTo(1L);
    	assertThat(updatedRolesPermition.getRole()).isEqualTo(role);
    	assertThat(updatedRolesPermition.getPermition()).isEqualTo(permition);
    	assertThat(updatedRolesPermition.getDateCreate()).isEqualTo(date);
}
  
  
  @Test
  public void givenRolesPermitionObject_whenDelete_thenRemoveRolesPermition(){
      
      rolesPermitionRepository.save(rolesPermition);

      rolesPermitionRepository.deleteById(rolesPermition.getId());
      Optional<RolesPermition> rolesPermitionOptional = rolesPermitionRepository.findById(rolesPermition.getId());

      assertThat(rolesPermitionOptional).isEmpty();
  }

}