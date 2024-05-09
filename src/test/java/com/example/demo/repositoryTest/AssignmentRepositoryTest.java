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

import com.example.demo.model.Assignment;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@DataJpaTest
public class AssignmentRepositoryTest {
	
	@Autowired 
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@InjectMocks
	private Assignment assignment;
	
	private User user;
	private Role role;
	private Date date = new Date(1615994763000L);
	
    @BeforeEach
    void setUp() {
    	
    	user = User.builder()
    			.username("Анна")
                .password("12345678")
                .email("Anna@gmail.com")
                .status("Активен")
                .build();
    	
    	role = Role.builder()
                .nameRole("Администратор")
                .description("Полные права доступа")
                .build();
    	
    	roleRepository.save(role);
    	userRepository.save(user);
    	
    	assignment = Assignment.builder()
    			.dateCreate(date)
    			.user(user)
    			.role(role)
                .build();
    }
    
    
    @Test
    public void givenAssignmentObject_whenSave_thenReturnSavedAssignment(){
    	
    	assignment = Assignment.builder()
    			.dateCreate(date)
    			.user(user)
    			.role(role)
                .build();

    	Assignment savedAssignment = assignmentRepository.save(assignment);
        assertThat(savedAssignment).isNotNull();
        assertThat(savedAssignment.getId()).isGreaterThan(0);
        
    }
    
    
    @Test
    public void givenAssignmentList_whenFindAll_thenAssignmentsList(){

    	Assignment assignment1 = Assignment.builder()
    			.dateCreate(date)
    			.user(user)
    			.role(role)
                .build();
    	
    	Integer listSize = assignmentRepository.findAll().size();
    	
    	System.out.println(assignmentRepository.findAll().size());
    	
    	assignmentRepository.save(assignment);
    	assignmentRepository.save(assignment1);
    	
    	System.out.println(assignmentRepository.findAll().size());

        List<Assignment> assignmentList = assignmentRepository.findAll();

        assertThat(assignmentList).isNotNull();
        assertThat(assignmentList.size()).isEqualTo(listSize + 2);
    }
    
    
    @Test
    public void givenAssignmentObject_whenFindById_thenReturnAssignmentObject(){

    	assignmentRepository.save(assignment);
    	
    	Assignment assignmentDB = assignmentRepository.findById(assignment.getId()).get();
    	
        assertThat(assignmentDB).isNotNull();
    }
    
    
    @Test
    public void givenAssignmentObject_whenUpdateAssignment_thenReturnUpdatedAssignment(){

    	assignmentRepository.save(assignment);
    	Date date = new Date(1615994763111L);

    	Assignment savedAssignment = assignmentRepository.findById(assignment.getId()).get();
    	savedAssignment.setId(1L);
    	savedAssignment.setDateCreate(date);
    	savedAssignment.setRole(role);
    	savedAssignment.setUser(user);
    	Assignment updatedAssignment =  assignmentRepository.save(savedAssignment);

        assertThat(updatedAssignment.getId()).isEqualTo(1L);
        assertThat(updatedAssignment.getDateCreate()).isEqualTo(date);
        assertThat(updatedAssignment.getRole()).isEqualTo(role);
        assertThat(updatedAssignment.getUser()).isEqualTo(user);
    }
    
    
    @Test
    public void givenAssignmentObject_whenDelete_thenRemoveAssignment(){

    	assignmentRepository.save(assignment);

    	assignmentRepository.deleteById(assignment.getId());
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignment.getId());

        assertThat(assignmentOptional).isEmpty();
    }
    
}







