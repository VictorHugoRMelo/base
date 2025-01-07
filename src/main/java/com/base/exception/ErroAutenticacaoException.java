package com.base.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ErroAutenticacaoException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	private String chave;

	@Getter
	private Object[] args;
	
	public ErroAutenticacaoException(String chave) {
		this.chave = chave;
	}

	public ErroAutenticacaoException(String chave, Exception e) {
		super(e);
		this.chave = chave;
	}

	public ErroAutenticacaoException(String chave, Object[] args, Exception e) {
		super(e);
		this.chave = chave;
		this.args = args;
	}
}