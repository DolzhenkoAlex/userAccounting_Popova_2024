package com.example.demo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "permitions")
public class Permition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name = "permition_name")
	@NotEmpty(message = "Вы должны ввести название права")
	private String permitionName;
	
	
	@Column(name = "description")
	@NotEmpty(message = "Вы должны ввести описание")
	private String description;
	

	@Column(name = "date_create")
	@NotNull(message = "Вы должны выбрать дату")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreate;

}
