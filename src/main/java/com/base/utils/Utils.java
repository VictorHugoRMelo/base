package com.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public final class Utils {

    private static final String EMPTY = "";
    private static final DecimalFormatSymbols DECIMAL_SYMBOLS = DecimalFormatSymbols.getInstance(Locale.getDefault());
    private static final Pattern ACENTOS_PATTERN = Pattern.compile("[^\\p{ASCII}]");

    public static String removerAcentosEEspacos(String str) {
        return Optional.ofNullable(str)
                .map(s -> ACENTOS_PATTERN.matcher(Normalizer.normalize(s, Normalizer.Form.NFD)).replaceAll("")
                        .replace(" ", ""))
                .orElse(EMPTY);
    }

    public static String removerAcentos(String str) {
        return Optional.ofNullable(str)
                .map(s -> ACENTOS_PATTERN.matcher(Normalizer.normalize(s, Normalizer.Form.NFD)).replaceAll(""))
                .orElse(EMPTY);
    }

    public static String completarEsquerdaString(String valor, String preenchimento, int tamanho) {
        Objects.requireNonNull(valor, "Valor não pode ser nulo.");
        Objects.requireNonNull(preenchimento, "Preenchimento não pode ser nulo.");
        if (valor.length() >= tamanho) return valor;
        return preenchimento.repeat(tamanho - valor.length()) + valor;
    }

    public static boolean contemCaracteresEspeciais(String linha) {
        return linha != null && linha.matches(".*[áàãâéèêíìîóòõôúùûçÁÀÂÉÈÊÍÌÎÓÒÕÔÚÙÛÇ].*");
    }

    public static Date localDateToDate(LocalDate data) {
        return Optional.ofNullable(data)
                .map(d -> Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .orElse(null);
    }

    public static LocalDate dateToLocalDate(Date data) {
        return Optional.ofNullable(data)
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .orElse(null);
    }

    public static LocalDateTime dateToLocalDateTime(Date data) {
        return Optional.ofNullable(data)
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .orElse(null);
    }

    public static String formatarLocalDateToString(LocalDate data, String formato) {
        return Optional.ofNullable(data)
                .map(d -> d.format(DateTimeFormatter.ofPattern(formato)))
                .orElse(EMPTY);
    }

    public static Date formatarStringToDate(String data, String formato) {
        try {
            return Optional.ofNullable(data)
                    .map(d -> LocalDate.parse(d, DateTimeFormatter.ofPattern(formato)))
                    .map(Utils::localDateToDate)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate adiantarParaProximoDiaUtil(LocalDate data) {
        return Optional.ofNullable(data)
                .map(d -> {
                    while (d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        d = d.plusDays(1);
                    }
                    return d;
                })
                .orElse(null);
    }

    public static LocalDate voltarParaPrimeiroDiaUtilMesAnterior(LocalDate data) {
        return Optional.ofNullable(data)
                .map(d -> {
                    LocalDate primeiroDiaMesAnterior = d.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
                    return adiantarParaProximoDiaUtil(primeiroDiaMesAnterior);
                })
                .orElse(null);
    }

    public static Timestamp obterTimestampInicioMenosMeses(LocalDate data, int meses) {
        return Optional.ofNullable(data)
                .map(d -> d.with(TemporalAdjusters.firstDayOfMonth()).minusMonths(meses))
                .map(LocalDate::atStartOfDay)
                .map(Timestamp::valueOf)
                .orElse(null);
    }

    public static Timestamp obterDiaDoMesAtualComoTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String obterMesAno(Date data) {
        return Optional.ofNullable(data)
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .map(d -> d.format(DateTimeFormatter.ofPattern("MMM/yyyy", Locale.getDefault())))
                .orElse(EMPTY);
    }

    public static String formatNumber(BigDecimal numero) {
        DecimalFormat formatter = new DecimalFormat("#,###.##", DECIMAL_SYMBOLS);
        return formatter.format(numero);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("Number of decimal places must be non-negative.");
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static String extrairNomeParametro(String parametro) {
        if (parametro == null || parametro.isBlank()) {
            return EMPTY;
        }
        int lastSlashIndex = parametro.lastIndexOf('/');
        return lastSlashIndex >= 0 ? parametro.substring(lastSlashIndex + 1) : parametro;
    }

	public static String converterDateParaString(Date data) {
		if (Objects.nonNull(data)) {
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(data);
		}
		return null;
	}

}
