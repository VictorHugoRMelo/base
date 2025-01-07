package com.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailException extends SistemaException {

	private static final long serialVersionUID = 1L;
	
	public EmailException(String msm) {
		super(msm);
	}

	public EmailException(Exception e) {
		super(e);
	}

	public EmailException(String msm, Exception e) {
		super(msm, e);
	}

}
