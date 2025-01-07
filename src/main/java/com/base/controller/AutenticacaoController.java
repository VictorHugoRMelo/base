package com.base.controller;


import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.exception.ErroAutenticacaoException;
import com.base.exception.NegocioException;
import com.base.request.LoginRequest;
import com.base.response.TokenResponse;
import com.base.service.HistoricoLoginService;
import com.base.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/autenticacao")
public class AutenticacaoController {
	
	private final UsuarioService usuarioService;
	private final HistoricoLoginService historicoLoginService;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest login) throws Exception {
		try {
			var jwtToken = usuarioService.autenticar(login.getEmail(), login.getSenha(), login.isPermanecerLogado());
			var token = TokenResponse.builder().token(jwtToken).build();
			if(Objects.nonNull(jwtToken)) {
				historicoLoginService.registrar(login.getEmail());
				return ResponseEntity.ok(token);
			}
		} catch (NegocioException | ErroAutenticacaoException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

}
