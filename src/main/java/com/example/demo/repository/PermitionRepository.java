package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Permition;

public interface PermitionRepository extends JpaRepository<Permition, Long>{

	Iterable<Permition> findAllByOrderByPermitionName();
	
	Permition findByPermitionName(String permitionName);
	
}
