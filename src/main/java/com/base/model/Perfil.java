package com.base.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

	private static final long serialVersionUID = 895971620249797457L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq")
    @SequenceGenerator(name = "perfil_seq", sequenceName = "perfil_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "ativado", nullable = false)
    private Boolean ativado;

}
