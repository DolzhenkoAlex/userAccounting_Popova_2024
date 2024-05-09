package com.example.demo.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

@DataJpaTest
public class RoleRepositoryTest {
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@InjectMocks
	private Role role;
	
    @BeforeEach
    void setUp() {
    	
    	role = Role.builder()
                .nameRole("Администратор")
                .description("Полные права доступа")
                .build();
    }
    
    
    @Test
    public void givenRoleObject_whenSave_thenReturnSavedRole(){
    	
    	role = Role.builder()
                .nameRole("Администратор")
                .description("Полные права доступа")
                .build();

    	Role savedRole = roleRepository.save(role);
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isGreaterThan(0);
        
    }
    

    @Test
    public void givenRoleList_whenFindAll_thenRolesList(){
    	   
    	   Role role1 = Role.builder()
    	           .nameRole("Модератор")
    	           .description("Возможность удалять статьи")
    	           .build();
    	   
    	   Integer listSize = roleRepository.findAll().size();
    	   
    	   roleRepository.save(role);
    	   roleRepository.save(role1);

    	   List<Role> roleList = roleRepository.findAll();

    	   assertThat(roleList).isNotNull();
    	   assertThat(roleList.size()).isEqualTo(listSize + 2);
    }
    
    
    @Test
    public void givenRoleObject_whenFindById_thenReturnRoleObject(){

       roleRepository.save(role);
       
       Role roleDB = roleRepository.findById(role.getId()).get();
       
       assertThat(roleDB).isNotNull();
    }
    
    
    @Test
    public void givenRoleObject_whenUpdateRole_thenReturnUpdatedRole(){
    	
    	roleRepository.save(role);

       Role savedRole = roleRepository.findById(role.getId()).get();
       savedRole.setId(1L);
       savedRole.setNameRole("Модератор");
       savedRole.setDescription("Возможность удалять статьи");
       

       Role updatedRole =  roleRepository.save(savedRole);

       assertThat(updatedRole.getId()).isEqualTo(1L);
       assertThat(updatedRole.getNameRole()).isEqualTo("Модератор");
       assertThat(updatedRole.getDescription()).isEqualTo("Возможность удалять статьи");
    }
    
    
    @Test
    public void givenRoleObject_whenDelete_thenRemoveRole(){
    	
    	roleRepository.save(role);

    	roleRepository.deleteById(role.getId());
    	Optional<Role> roleOptional = roleRepository.findById(role.getId());

    	assertThat(roleOptional).isEmpty();
    }

}
