package util;

import javax.swing.text.View;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static <E extends Enum<E>> E getTextWithEnum(
            Class<E> enumClass,
            String label
    ) {
        while (true) {
            ViewUtil.print(label + " -> ", false);
            String input = scanner.nextLine();
            if (input.equals("0")) {
                return null; //test
            }
            if (input.isEmpty()) {
                ViewUtil.printHeader("Invalid option.");
                continue;
            }

            try {
                return Enum.valueOf(enumClass, input.toUpperCase());
            } catch (IllegalArgumentException e) {
                ViewUtil.printHeader("Invalid option. Please choose from:");
                for (E value : enumClass.getEnumConstants()) {
                    System.out.println("- " + value);
                }
            }
        }
    }


    public static boolean isValidPhone(String phone) {
        // Digits only, length 8–15
        String phoneRegex = "^[0-9]{8,15}$";
        return Pattern.matches(phoneRegex, phone);
    }

    public static String getText(String label) {
        do{
            ViewUtil.print(label + "-> ", false);
            String s = scanner.nextLine();
            if(s.isEmpty()){
                ViewUtil.printHeader("Invalid Options.");
                continue;
            }
            return s;
        }while (true);

    }

    public static BigDecimal getMoney(String label) {
        ViewUtil.print(label + "-> ", false);
        return scanner.nextBigDecimal();
    }


    public static Integer getIntegerMoreThanZero(String label) {
        do {
            ViewUtil.print(label + "-> ", false);
            try {

                Integer integer = Integer.parseInt(scanner.nextLine());
                if(integer == 0 ){
                    return 0;
                }
                if (integer > 0){
                  return integer;
                }else {
                    ViewUtil.printHeader("Invalid Number! ");
                }
            } catch (NumberFormatException e) {
                ViewUtil.printHeader(e.getMessage());
            }
        } while(true);
    }


    public static Integer getInteger(String label) {
        do {
            ViewUtil.print(label + "-> ", false);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                ViewUtil.print(e.getMessage(), true);
            }
        } while(true);
    }

    public static Double getDouble(String label) {
        do {
            ViewUtil.print(label + "-> ", false);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                ViewUtil.print(e.getMessage(), true);
            }
        } while(true);
    }

}
