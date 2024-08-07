package mitch.prisonscore.modules.profile.utils;


import java.math.BigDecimal;
import java.math.BigInteger;

public class CurrencyUtils {

    private static final String[] suffixes = {"", "k", "m", "b", "t", "q", "a", "c", "d", "e", "f", "g", "h", "i", "j"};

    public static String format(long number) {
        return format(BigInteger.valueOf(number));
    }

    public static String format(BigInteger number) {
        if (number.compareTo(BigInteger.valueOf(1000)) < 0) {
            return number.toString();
        }
        int magnitude = (int) (Math.log10(number.doubleValue()) / 3);
        double convertedNumber = number.doubleValue() / Math.pow(1000, magnitude);
        return String.format("%.1f%s", convertedNumber, suffixes[magnitude]);
    }


    public static BigInteger parse(String amount){
        BigInteger amountInt;

        char lastChar = amount.charAt(amount.length() - 1);

        if (Character.isDigit(lastChar)) {
            try{
                amountInt = BigInteger.valueOf(Long.parseLong(amount));
            }catch (NumberFormatException e){
                return BigInteger.valueOf(-1);
            }
        } else {
            char letter = amount.substring(amount.length() - 1).toLowerCase().charAt(0);
            double doubleAmount = Double.parseDouble(amount.substring(0, amount.length() - 1));
            amountInt = change(letter, doubleAmount);
        }

        return amountInt;
    }

    private static BigInteger change(char letter, double amountInt){
        BigInteger result;
        BigDecimal amountDecimal = BigDecimal.valueOf(amountInt);

        switch (letter) {
            case 'k' -> result = amountDecimal.multiply(BigDecimal.valueOf(1000)).toBigInteger();
            case 'm' -> result = amountDecimal.multiply(BigDecimal.valueOf(1000000)).toBigInteger();
            case 'b' -> result = amountDecimal.multiply(BigDecimal.valueOf(1000000000)).toBigInteger();
            case 't' -> result = amountDecimal.multiply(BigDecimal.valueOf(1000000000000L)).toBigInteger();
            case 'q' -> result = amountDecimal.multiply(BigDecimal.valueOf(1000000000000000L)).toBigInteger();
            case 'a' -> result = amountDecimal.multiply(BigDecimal.valueOf(1000000000000000000L)).toBigInteger();
            case 'c' -> result = amountDecimal.multiply(new BigDecimal("1e21")).toBigInteger();
            case 'd' -> result = amountDecimal.multiply(new BigDecimal("1e24")).toBigInteger();
            case 'e' -> result = amountDecimal.multiply(new BigDecimal("1e27")).toBigInteger();
            case 'f' -> result = amountDecimal.multiply(new BigDecimal("1e30")).toBigInteger();
            case 'g' -> result = amountDecimal.multiply(new BigDecimal("1e33")).toBigInteger();
            case 'h' -> result = amountDecimal.multiply(new BigDecimal("1e36")).toBigInteger();
            case 'i' -> result = amountDecimal.multiply(new BigDecimal("1e39")).toBigInteger();
            case 'j' -> result = amountDecimal.multiply(new BigDecimal("1e42")).toBigInteger();
            default -> result = BigInteger.valueOf(-1);
        }
        return result;
    }
}
