package com.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.model.PacienteModel;


public interface PacienteRepository extends JpaRepository<PacienteModel, Long>{

}
