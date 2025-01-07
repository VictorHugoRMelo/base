package com.base.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AmbienteEnum {
	
	PRO("Produção"),
	HOM("Homologação"),
	DEV("Desenvolvimento");
	
	private String nome;
	
}

