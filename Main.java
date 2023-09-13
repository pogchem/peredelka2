import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 1 + 2):");

        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws IllegalArgumentException {
        // Создаем карты для преобразования римских и арабских чисел
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);

        Map<Integer, String> arabicNumerals = new HashMap<>();
        arabicNumerals.put(1, "I");
        arabicNumerals.put(2, "II");
        arabicNumerals.put(3, "III");
        arabicNumerals.put(4, "IV");
        arabicNumerals.put(5, "V");
        arabicNumerals.put(6, "VI");
        arabicNumerals.put(7, "VII");
        arabicNumerals.put(8, "VIII");
        arabicNumerals.put(9, "IX");
        arabicNumerals.put(10, "X");
        arabicNumerals.put(50, "L");
        arabicNumerals.put(100, "C");
        arabicNumerals.put(500, "D");
        arabicNumerals.put(1000, "M");

        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        try {
            String num1Str = parts[0];
            String operator = parts[1];
            String num2Str = parts[2];

            boolean isRoman1 = isRoman(num1Str);
            boolean isRoman2 = isRoman(num2Str);

            if (isRoman1 && isRoman2) {
                int num1 = convertRomanToArabic(num1Str, romanNumerals);
                int num2 = convertRomanToArabic(num2Str, romanNumerals);

                if (num1 < 1 || num1 > 1000 || num2 < 1 || num2 > 1000) {
                    throw new IllegalArgumentException("Числа должны быть от 1 до 1000 включительно");
                }

                int result;
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        return convertArabicToRoman(result, arabicNumerals);
                    case "-":
                        result = num1 - num2;
                        return convertArabicToRoman(result, arabicNumerals);
                    case "*":
                        result = num1 * num2;
                        return convertArabicToRoman(result, arabicNumerals);
                    case "/":
                        if (num2 == 0) {
                            throw new IllegalArgumentException("Деление на ноль");
                        }
                        result = num1 / num2;
                        return convertArabicToRoman(result, arabicNumerals);
                    default:
                        throw new IllegalArgumentException("Некорректная операция");
                }
            } else if (!isRoman1 && !isRoman2) {
                int num1 = Integer.parseInt(num1Str);
                int num2 = Integer.parseInt(num2Str);

                if (num1 < 1 || num1 > 1000 || num2 < 1 || num2 > 1000) {
                    throw new IllegalArgumentException("Числа должны быть от 1 до 1000 включительно");
                }

                int result;
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        return String.valueOf(result);
                    case "-":
                        result = num1 - num2;
                        return String.valueOf(result);
                    case "*":
                        result = num1 * num2;
                        return String.valueOf(result);
                    case "/":
                        if (num2 == 0) {
                            throw new IllegalArgumentException("Деление на ноль");
                        }
                        result = num1 / num2;
                        return String.valueOf(result);
                    default:
                        throw new IllegalArgumentException("Некорректная операция");
                }
            } else {
                throw new IllegalArgumentException("Некорректные числа");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа");
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("^[IVXLCDM]+$");
    }

    private static int convertRomanToArabic(String roman, Map<Character, Integer> romanNumerals) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int curValue = romanNumerals.get(roman.charAt(i));
            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }

        return result;
    }

    private static String convertArabicToRoman(int arabic, Map<Integer, String> arabicNumerals) {
        if (arabic <= 0 || arabic > 1000) {
            throw new IllegalArgumentException("Число должно быть от 1 до 1000 включительно");
        }

        StringBuilder roman = new StringBuilder();

        while (arabic >= 1000) {
            roman.append("M");
            arabic -= 1000;
        }

        while (arabic >= 900) {
            roman.append("CM");
            arabic -= 900;
        }

        while (arabic >= 500) {
            roman.append("D");
            arabic -= 500;
        }

        while (arabic >= 400) {
            roman.append("CD");
            arabic -= 400;
        }

        while (arabic >= 100) {
            roman.append("C");
            arabic -= 100;
        }

        while (arabic >= 90) {
            roman.append("XC");
            arabic -= 90;
        }

        while (arabic >= 50) {
            roman.append("L");
            arabic -= 50;
        }

        while (arabic >= 40) {
            roman.append("XL");
            arabic -= 40;
        }

        while (arabic >= 10) {
            roman.append("X");
            arabic -= 10;
        }

        {
            while (arabic >= 9) {
                roman.append("IX");
                arabic -= 9;
            }

            while (arabic >= 5) {
                roman.append("V");
                arabic -= 5;
            }

            while (arabic >= 4) {
                roman.append("IV");
                arabic -= 4;
            }

            while (arabic >= 1) {
                roman.append("I");
                arabic -= 1;
            }

            return roman.toString();
        }
    }
    }