package com.base.service;


import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.base.event.IdentificadorEmail;

import org.slf4j.Logger;

@Component
public class EnviarEmail {
    private static final Logger logger = LoggerFactory.getLogger(EnviarEmail.class);

	@EventListener
	public void usuarioEvent(IdentificadorEmail paciente) {
		logger.info("EMAIL ENVIADO para : " + paciente.getIdentificador() );
	}
}
