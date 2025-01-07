package com.base.email;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HtmlTableGenerator {

	private static final String COR_F7F7F7 = "F7F7F7";
	private static final String COR_CCCCCC = "CCCCCC";
	private static final String COR_FFFFFF = "FFFFFF";
	private String corAtual = COR_FFFFFF;
	private LinkedHashMap<String, Object> dados;

	public String montarBody() {
		String ret = "";
		for (String key : dados.keySet()) {
			Object value = dados.get(key);
			if (Objects.nonNull(value)) {
				String linhaNova = getLinha();
				linhaNova = linhaNova.replace("{1}", key);
				linhaNova = linhaNova.replace("{2}", value.toString());
				if (corAtual.equalsIgnoreCase(COR_FFFFFF)) {
					linhaNova = linhaNova.replace("{3}", COR_FFFFFF);
					linhaNova = linhaNova.replace("{4}", COR_FFFFFF);
					corAtual = COR_F7F7F7;
				} else {
					linhaNova = linhaNova.replace("{3}", COR_F7F7F7);
					linhaNova = linhaNova.replace("{4}", COR_F7F7F7);
					corAtual = COR_FFFFFF;
				}
				ret += linhaNova;
			} else {
				String linhaNova = getLinhaMesclada();
				linhaNova = linhaNova.replace("{1}", key);
				linhaNova = linhaNova.replace("{3}", COR_CCCCCC);
				ret += linhaNova;

			}

		}
		return ret;
	}

	public Map<String, Object> getDados() {
		if (Objects.isNull(dados)) {
			dados = new LinkedHashMap<String, Object>();
		}
		return dados;
	}

	private String getLinha() {
		StringBuilder html = new StringBuilder();
		html.append("<tr><td bgcolor=\"#{3}\" width='15%' style=\"margin:10px\" align=\"right\"><span align=\"right\" nowrap=\"nowrap\"><strong>{1}</strong></span></td>");
		html.append("<td bgcolor=\"#{4}\" align=\"left\"><span align=\"left\" style=\"margin:10px\">{2}</span></td></tr>");
		return html.toString();
	}

	private String getLinhaMesclada() {
		StringBuilder html = new StringBuilder();
		html.append("<tr>");
		html.append("<td colspan=\"2\" bgcolor=\"#{3}\" style=\"margin:10px\" align=\"center\"><span align=\"center\" nowrap=\"nowrap\"><strong>{1}</strong></span></td>");
		html.append("</tr>");
		return html.toString();
	}

	
}

