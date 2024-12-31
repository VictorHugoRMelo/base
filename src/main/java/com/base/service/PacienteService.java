package com.base.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.exception.ResourceNotFoundException;
import com.base.model.PacienteModel;
import com.base.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteService {
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	

	public PacienteModel savePaciente(PacienteModel paciente) {
		PacienteModel novoPaciente = new PacienteModel();
		novoPaciente.setNome(paciente.getNome());
		novoPaciente.setTelefone(paciente.getTelefone());
		PacienteModel salvouProduto = pacienteRepository.save(novoPaciente);
		return salvouProduto;
	}
	
	public PacienteModel upDatePaciente(Long id, PacienteModel paciente) {
		PacienteModel existingUser = pacienteRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	
	    existingUser.setNome(paciente.getNome());
	    existingUser.setTelefone(paciente.getTelefone());
	    return pacienteRepository.save(existingUser);
	}
	
	public void deleteId(Long id) {
		Optional<PacienteModel> existingUser = pacienteRepository.findById(id);
		if(existingUser.isPresent()) {
			pacienteRepository.deleteById(id);
		}
	}
	
	 public PacienteModel getUserById(Long id) {
        return pacienteRepository.findById(id).orElseThrow(() -> 
        new ResourceNotFoundException("Paciente com id : " + id + " Não foi encontrado"));
	}
	 
	 public List<PacienteModel> buscarTodos() {
		    List<PacienteModel> paciente = pacienteRepository.findAll();
		    return paciente;
	 }	    		
}
