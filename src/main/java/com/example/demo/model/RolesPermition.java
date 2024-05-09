package com.example.demo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "rolespermitions")
public class RolesPermition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "Вы должны выбрать роль")
    private Role role;

	
	@ManyToOne
    @JoinColumn(name = "permition_id")
    @NotNull(message = "Вы должны выбрать право")
    private Permition permition;
	
	
	@Column(name = "date_create")
	@NotNull(message = "Вы должны выбрать дату")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreate;
}






