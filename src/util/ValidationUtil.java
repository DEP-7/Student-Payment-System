package util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class ValidationUtil {
    public static  boolean isValidEmail(String text) {
        return text.matches("^[^\\W_][\\w.]*[^\\W_]@([^\\W_]+[.]?)+[^\\W_][.][^\\W_]{2,3}$");
    }

    public static  boolean isValidContactNumber(String text) {
        return text.matches("[0]\\d{2}-\\d{7}");
    }

    public static  boolean isValidAddress(String text) {
        return text.matches("([^\\W_]|[-.,/\\\\ ]){4,}");
    }

    public static  boolean isValidPercentage(String text) {
        if (!text.matches("\\d+([.]\\d+|\\d*)")) {
            return false;
        }
        BigDecimal value = new BigDecimal(text);
        return value.compareTo(new BigDecimal("100")) < 0 && value.compareTo(new BigDecimal("0")) >= 0;
    }

    public static  boolean isValidPastDate(String text) {
        try {
            LocalDate past = LocalDate.parse(text);
            LocalDate today = LocalDate.now();
            Period period = Period.between(past, today);
            return !period.isNegative();
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static  boolean isValidNameWithInitials(String text) {
        return text.matches("[A-Za-z .]{3,}");
    }

    public static  boolean isValidFullName(String text) {
        return text.matches("[A-Za-z ]{3,}");
    }

    public static  boolean isValidNIC(String inputNIC) {
        return inputNIC.matches("([89]\\d([0-3]|[5-8])\\d{6}[Vv])|(([1][9][89]|[2][01]\\d)\\d([0-3]|[5-8])\\d{7})");
    }
}
