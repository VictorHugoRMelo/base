package com.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SistemaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SistemaException(String msm) {
		super(msm);
	}

	public SistemaException(Exception e) {
		super(e);
	}

	public SistemaException(String msm, Exception e) {
		super(msm, e);
	}

}
