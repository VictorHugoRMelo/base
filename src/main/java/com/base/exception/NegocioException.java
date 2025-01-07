package com.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private String chave;

	@Getter
	private Object[] args;
	
	public NegocioException(String chave) {
		this.chave = chave;
	}

	public NegocioException(String chave, Object... args) {
		this.chave = chave;
		this.args = args;
	}

}
