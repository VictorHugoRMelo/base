package com.base.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerfilUsuarioEnum {
	
	ADMIN(1l, "Administrador"),
	ESPEC(2l, "Especialista"),
	ANALI(3l, "Analista"),
	OPERA(1l, "Operacional"),
	USINA(2l, "Usina"),
	CONSU(3l, "Consulta");
	
	private Long id;
	private String descricao;
	
}
