package com.base.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest implements Serializable {
	
	private static final long serialVersionUID = -6672444451133381632L;

	@NotNull(message = "{usuario.email.not-null}")
	@NotBlank(message = "{usuario.email.not-blank}")
    private String email;
	
	@NotNull(message = "{usuario.senha.not-null}")
	@NotBlank(message = "{usuario.senha.not-blank}")
    private String senha;
	
	private boolean permanecerLogado;
}

