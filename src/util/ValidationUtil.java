package util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class ValidationUtil {
    public static boolean isValidEmail(String text) {
        return text.matches("^[^\\W_][\\w.]*[^\\W_]@([^\\W_]+[.]?)+[^\\W_][.][^\\W_]{2,3}$");
    }

    public static boolean isValidPastDate(String text) {
        return isValidPastDate(text, 0);
    }

    public static boolean isValidPastDate(String text, int addDays) {
        try {
            LocalDate past = LocalDate.parse(text);
            LocalDate today = LocalDate.now().plusDays(addDays);
            Period period = Period.between(past, today);
            return !period.isNegative();
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidNIC(String inputNIC) {
        return inputNIC.matches("([89]\\d([0-3]|[5-8])\\d{6}[Vv])|(([1][9][89]|[2][01]\\d)\\d([0-3]|[5-8])\\d{7})");
    }
}
