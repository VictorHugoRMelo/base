package com.base.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ConverterUtil {
	
	private static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	private static final String DD_MM_YYYY = "dd/MM/yyyy";
	private static final String HH_MM_SS = "HH:mm:ss";
	private static final String _VIRGULA = ",";
	private static final String _PONTO = ".";
	private static final String _VAZIO = "";

	public static Integer converterStringEmInteger(String valor) {
		if(Objects.isNull(valor)|| valor.isEmpty()) {
			return null;
		}
		return Integer.valueOf(valor);
	}

	public static Long converterStringEmLong(String valor) {
		if(Objects.isNull(valor)|| valor.isEmpty()) {
			return null;
		}
		return Long.valueOf(valor);
	}
	
	public static BigInteger converterStringEmBigIntegerPodendoVirTexto(String valor) {
		try {
			if(Objects.isNull(valor)|| valor.isEmpty()) {
				return null;
			}
			return new BigInteger(valor);
		}catch (Exception e) {
			return null;
		}
	}
	
	public static BigInteger converterStringEmBigInteger(String valor) {
		if(Objects.isNull(valor)|| valor.isEmpty()) {
			return null;
		}
		return new BigInteger(valor);
	}
	
	public static LocalDate converterStringEmData(String valor) {
		if(Objects.isNull(valor) || valor.isEmpty()) {
			return null;
		}
		return LocalDate.parse(valor.trim(), DateTimeFormatter.ofPattern(DD_MM_YYYY));
	}

	public static LocalDate converterStringEmData(String valor, String pattern) {
		if(Objects.isNull(valor) || valor.isEmpty()) {
			return null;
		}
		return LocalDate.parse(valor.trim(), DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime converterStringEmDataHora(String valor) {
		if(Objects.isNull(valor) || valor.isEmpty()) {
			return null;
		}
		return LocalDateTime.parse(valor.trim(), DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS));
	}
	
	public static LocalTime converterStringEmHoraMinuto(String valor) {
		if(Objects.isNull(valor) || valor.isEmpty()) {
			return null;
		}
		return LocalTime.parse(valor.trim(), DateTimeFormatter.ofPattern(HH_MM_SS));
	}
	
	public static BigDecimal converterStringParaBigDecimal(String valor) {
		if(Objects.isNull(valor) || valor.isEmpty()) {
			return null;
		}
		String val = _VAZIO;
		if(valor.trim().contains(_VIRGULA) && valor.trim().contains(_PONTO)) { // 2.333,33
			val = valor.trim().replace(_PONTO,_VAZIO).replace(_VIRGULA, _PONTO);
		} else if(valor.trim().contains(_VIRGULA) && !valor.trim().contains(_PONTO)) { // 0,2
			val = valor.trim().replace(_VIRGULA, _PONTO);
		} else if(valor.trim().contains(_PONTO) && !valor.trim().contains(_VIRGULA)) { // 1.999
			val = valor.trim().replace(_PONTO, _VAZIO);
		} else {
			val = valor.trim();
		}
		BigDecimal ret = new BigDecimal(val);
		return ret;
	}	

	public static String extrairString(String valor) {
		if(Objects.isNull(valor) || valor.isEmpty()) {
			return valor;
		}
		return valor.trim().replace("\"", "").replace("'", "");
	}

}
