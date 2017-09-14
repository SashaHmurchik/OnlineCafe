package by.epam.cafe.util;

import java.util.Random;

public class PasswordCreator {
    /**
     * Method for create valid password.
     * @return valid password.
     */
    public static String getPassword() {
        String result = "";
        for(int i = 0; i < 2; i++) {
            String c = String.valueOf((char)('A' + new Random().nextInt(26)));
            if (i == 0) {
                c = c.toLowerCase();
            }
            result = result.concat(c);
        }
        for(int i = 0; i < 8; i++) {
            result = result.concat(String.valueOf(new Random().nextInt(9)));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getPassword().toString());
    }
}
