package com.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

import com.base.enuns.ProcessamentoTipoEnum;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ArquivoUtils {

    public static final int LIMITE_LINHAS_INSERIR = 30000;
    public static final int LIMITE_LINHAS_ERRO = 5000;
    private static final String UTF8_BOM = "\uFEFF";

    public static String removeUTF8BOM(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.startsWith(UTF8_BOM))
                .map(str -> str.substring(1))
                .orElse(s);
    }

    public static String adiquirirCharsetDoArquivo(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = removeUTF8BOM(scanner.nextLine());
                if (line.matches(".*[áàãâéèêíìîóòõôúùûçÁÀÂÉÈÊÍÌÎÓÒÕÔÚÙÛÇ].*")) {
                    return StandardCharsets.UTF_8.name();
                }
            }
            return StandardCharsets.ISO_8859_1.name();
        } catch (Exception e) {
            return StandardCharsets.UTF_8.name();
        }
    }

    public static boolean validarCabecalhoPontoVirgula(String cabecalho, String stringValidacao) {
        String[] cab = cabecalho.split(";");
        String[] val = stringValidacao.split(";");
        for (int i = 0; i < val.length; i++) {
            if (!val[i].trim().equalsIgnoreCase(cab[i].trim())) {
                return false;
            }
        }
        return true;
    }

    public static boolean validarCabecalhoVirgula(String cabecalho, String stringValidacao) {
        String[] cab = cabecalho.split(",");
        String[] val = stringValidacao.split(",");
        for (int i = 0; i < val.length; i++) {
            if (!val[i].trim().equalsIgnoreCase(cab[i].trim())) {
                return false;
            }
        }
        return true;
    }

    public static String[] extrairArrayPontoVirgula(String ln, ProcessamentoTipoEnum tpProc) {
        String[] cabecalho = tpProc.getStringValidacao().split(";");
        String[] ret = ln.split(";", -1);
        if (cabecalho.length != ret.length || ehLinhaBranco(ret, cabecalho.length)) {
            return null;
        }
        return validarCabecalhoPontoVirgula(ln, tpProc.getStringValidacao()) ? null : ret;
    }

    public static String[] extrairArrayVirgula(String ln, ProcessamentoTipoEnum tpProc) {
        String[] cabecalho = tpProc.getStringValidacao().split(",");
        String[] ret = ln.split(",", -1);
        if (cabecalho.length != ret.length || ehLinhaBranco(ret, cabecalho.length)) {
            return null;
        }
        if (validarCabecalhoPontoVirgula(ln, tpProc.getStringValidacao())) {
            return null;
        }
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ret[i].replaceAll("^\"|\"$", "");
        }
        return ret;
    }

    private static boolean ehLinhaBranco(String[] ret, int length) {
        int contEmBranco = 0;
        int qtdColunaValidar = Math.min(ret.length, 4);
        for (int i = 0; i < qtdColunaValidar; i++) {
            if (ret[i].isEmpty()) {
                contEmBranco++;
            }
        }
        return contEmBranco == qtdColunaValidar;
    }
}
