package com.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.base.event.IdentificadorEmail;
import com.base.model.PacienteModel;
import com.base.service.PacienteService;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	PacienteService pacienteService;
	
	@Autowired
	private ApplicationEventPublisher evento;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private PacienteModel savePaciente(@RequestBody PacienteModel paciente) {
		evento.publishEvent(new IdentificadorEmail(paciente.getNome(), null));
	return pacienteService.savePaciente(paciente);
	}
	
<<<<<<< HEAD
	
    @PutMapping("/{ids}")
=======
    @PutMapping("/{id}")
>>>>>>> refs/heads/develop
    public ResponseEntity<PacienteModel> upDatePaciente(@PathVariable Long id, @RequestBody PacienteModel userDetails) {
    	PacienteModel updatedUser = pacienteService.upDatePaciente(id, userDetails);
    	return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteId(@PathVariable Long id) {
    	pacienteService.deleteId(id);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PacienteModel> getUserById(@PathVariable Long id) {
    	PacienteModel user = pacienteService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    public ResponseEntity<List<PacienteModel>> buscarTodos() {
        List<PacienteModel> products = pacienteService.buscarTodos();
        return ResponseEntity.ok(products);
    }
}
