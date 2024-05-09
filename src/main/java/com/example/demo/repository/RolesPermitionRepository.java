package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.RolesPermition;

public interface RolesPermitionRepository extends JpaRepository<RolesPermition, Long>{
	
	@Query(value = "SELECT rolespermitions.* FROM rolespermitions "
			+ "JOIN roles AS roles ON rolespermitions.role_id = roles.id "
			+ "ORDER BY roles.name_role ,role_id, permition_id", nativeQuery = true)
	Iterable<RolesPermition> findAllByOrderByRoleNameAndPermition();

	
	RolesPermition findByPermitionIdAndRoleId(Long permitionId, Long roleId);
	
	
	Iterable<RolesPermition> findAllByRoleId(Long roleId);
}
