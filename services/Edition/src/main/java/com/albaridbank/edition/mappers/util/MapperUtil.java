package com.albaridbank.edition.mappers.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MapperUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Formate un montant avec deux décimales et séparateur de milliers
     */
    public static String formatMontant(BigDecimal montant) {
        if (montant == null) {
            return "0,00";
        }
        return String.format(Locale.FRANCE, "%,.2f", montant.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Formate une date au format dd/MM/yyyy
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Formate une date et heure au format dd/MM/yyyy HH:mm:ss
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * Convertit un code d'état en libellé pour les comptes CCP
     */
    public static String mapEtatCompteCCP(String codeEtat) {
        if (codeEtat == null) return "";
        return switch (codeEtat) {
            case "A" -> "ACTIF";
            case "C" -> "CLÔTURÉ";
            case "I" -> "INACTIF";
            case "B" -> "BLOQUÉ";
            default -> codeEtat;
        };
    }

    /**
     * Convertit un code d'état en libellé pour les comptes CEN
     */
    public static String mapEtatCompteCEN(Integer codeTypeActivite) {
        if (codeTypeActivite == null) return "INCONNU";
        return switch (codeTypeActivite) {
            case 1 -> "ACTIF";
            case 2 -> "INACTIF";
            case 3 -> "BLOQUÉ";
            case 4 -> "CLÔTURÉ";
            default -> "INCONNU";
        };
    }
}