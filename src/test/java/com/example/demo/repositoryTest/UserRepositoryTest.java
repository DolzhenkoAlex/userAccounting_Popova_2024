package com.example.demo.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
   
   @Autowired 
   private UserRepository userRepository;
   
   @InjectMocks
   private User user;
   
   @BeforeEach
   void setUp() {
       
   	user = User.builder()
   			.username("Анна")
            .password("12345678")
            .email("Anna@gmail.com")
            .status("Активен")
            .build();
   }
   
   
   @Test
   public void givenUserObject_whenSave_thenReturnSavedUser(){
       
       user = User.builder()
    		   .username("Анна")
    		   .password("12345678")
    		   .email("Anna@gmail.com")
    		   .status("Активен")
    		   .build();

       User savedUser = userRepository.save(user);
       assertThat(savedUser).isNotNull();
       assertThat(savedUser.getId()).isGreaterThan(0);
       
   }
   
   
   @Test
   public void givenUserList_whenFindAll_thenUsersList(){
          
          User user1 = User.builder()
        		  .username("Дима")
        		  .password("87654321")
        		  .email("Dima@gmail.com")
        		  .status("Не активен")
        		  .build();
          
          Integer listSize = userRepository.findAll().size();
          
          userRepository.save(user);
          userRepository.save(user1);

          List<User> userList = userRepository.findAll();

          assertThat(userList).isNotNull();
          assertThat(userList.size()).isEqualTo(listSize + 2);
          
   }
   
   
   @Test
   public void givenUserObject_whenFindById_thenReturnUserObject(){

      userRepository.save(user);
      
      User userDB = userRepository.findById(user.getId()).get();
      
      assertThat(userDB).isNotNull();
   }
   
   
   @Test
   public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser(){
       
       userRepository.save(user);

      User savedUser = userRepository.findById(user.getId()).get();
      savedUser.setId(1L);
      savedUser.setUsername("Дима");
      savedUser.setPassword("87654321");
      savedUser.setEmail("Dima@gmail.com");
      savedUser.setStatus("Не активен");

      User updatedUser =  userRepository.save(savedUser);

      assertThat(updatedUser.getId()).isEqualTo(1L);
      assertThat(updatedUser.getUsername()).isEqualTo("Дима");
      assertThat(updatedUser.getPassword()).isEqualTo("87654321");
      assertThat(updatedUser.getEmail()).isEqualTo("Dima@gmail.com");
      assertThat(updatedUser.getStatus()).isEqualTo("Не активен");
   }
   
   
   @Test
   public void givenUserObject_whenDelete_thenRemoveUser(){
       
       userRepository.save(user);

       userRepository.deleteById(user.getId());
       Optional<User> userOptional = userRepository.findById(user.getId());

       assertThat(userOptional).isEmpty();
   }

}