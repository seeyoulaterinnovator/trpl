import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {
    private final static String[] ARABIC = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private final static String[] ROMAN = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private final static String[] OPERATIONS = {"+", "-", "*", "/"};

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input:");
        System.out.println("Output:\n" + calc(reader.readLine()));
        reader.close();
    }

    public static String calc(String input) {
        if (input == null) throw new IllegalArgumentException();

        final String[] values = input.trim().split(" ");

        if (values.length != 3) throw new IllegalArgumentException();

        switch (handlerSystems(values)) {
            case ARABIC_NUM: {
                return String.valueOf(getResult(values));
            }

            case ROMAN_NUM: {
                int result = getResult(values);
                if (result > 0) {
                    return getRomanNumber(result);
                }
            }

            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    private static String getRomanNumber(int result) {
        if (String.valueOf(result).length() == 1) return ROMAN[result - 1];
        else if (String.valueOf(result).length() == 2) {
            StringBuilder sb = new StringBuilder();
            List<Dozens> dozens = Arrays.asList(Dozens.values());
            int i = 0;
            while (result > 9) {
                Dozens currentDozen = dozens.get(i);
                if (currentDozen.value <= result) {
                    sb.append(currentDozen.name());
                    result -= currentDozen.value;
                } else i++;
            }
            return result % 10 == 0 ? sb.toString() : sb.append(ROMAN[result - 1]).toString();
        }
        else return "C";
    }

    private static Const handlerSystems(String[] values) {
        if (!contains(OPERATIONS, values[1])) return Const.EXCEPTION;

        if (contains(ARABIC, values[0])) {
            if (contains(ARABIC, values[2])) return Const.ARABIC_NUM;
        } else if (contains(ROMAN, values[0])) {
            if (contains(ROMAN, values[2])) return Const.ROMAN_NUM;
        }

        return Const.EXCEPTION;
    }

    private static int getResult(String[] values) {
        switch (values[1]) {
            case "+":
                return getNumber(values[0]) + getNumber(values[2]);
            case "-":
                return getNumber(values[0]) - getNumber(values[2]);
            case "*":
                return getNumber(values[0]) * getNumber(values[2]);
            default:
                return getNumber(values[0]) / getNumber(values[2]);
        }
    }

    private static int getNumber(String value) {
        for (int i = 0; i < 10; i++) {
            if (ARABIC[i].equals(value)) return i + 1;
            else if (ROMAN[i].equals(value)) return i + 1;
        }
        throw new IllegalArgumentException();
    }

    private static boolean contains(String[] values, String value) {
        for (String s : values) {
            if (s.equals(value)) return true;
        }
        return false;
    }

    enum Const {
        ROMAN_NUM, ARABIC_NUM, EXCEPTION
    }

    enum Dozens {
        XC(90), L(50), XL(40), X(10);

        private final int value;

        Dozens(int value) {
            this.value = value;
        }
    }
}
