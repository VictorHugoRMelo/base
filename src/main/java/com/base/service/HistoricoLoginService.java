package com.base.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.base.exception.NegocioException;
import com.base.model.HistoricoLogin;
import com.base.model.HistoricoLoginId;
import com.base.repository.HistoricoLoginRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class HistoricoLoginService {

    private final HistoricoLoginRepository repository;
    private final UsuarioService usuarioService;

    @Transactional
    public void registrar(String email) {
        var usuario = usuarioService.findByEmail(email);
		if (Objects.nonNull(usuario)) {
			var histId = HistoricoLoginId.builder()
					.usuario(usuario)
					.dataAcesso(LocalDateTime.now()).build();
			var hist = HistoricoLogin.builder()
					.id(histId)
					.build();
			repository.save(hist);
		} else {
			throw new NegocioException("usuario-nao-encontrado");
		}
		
    }

}
