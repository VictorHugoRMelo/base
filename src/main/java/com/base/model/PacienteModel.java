package com.base.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pacientes")
public class PacienteModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	 @GeneratedValue
	private Long id;
	private String nome;
	private Long telefone;

}
