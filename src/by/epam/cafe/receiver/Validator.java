package by.epam.cafe.receiver;

public abstract class Validator {
    private static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[\\W|_]).{8,20}";
    private static final String PHONE_REGEX = "\\d{12}";
    private static final String PASSPORT_REGEX = "[A-Z]{2}\\d{7}";
    private static final String NAME_REGEX = "^[А-ЯA-Z][a-яa-z]{3,24}";
    private static final String SURNAME_REGEX = "^[А-ЯA-Z][a-яa-z]{3,24}";

    public static boolean isPasswordValid(final String password) {
        return (password != null) && password.matches(PASSWORD_REGEX);
    }

    public static boolean isEmailValid(final String eMail) {
        return (eMail != null) && eMail.matches(EMAIL_REGEX);
    }


    public static boolean isSurnameValid(final String surname) {
        return (surname != null) && surname.matches(SURNAME_REGEX);
    }

    public static boolean isPhoneValid(String phone) {
         return (phone != null) && phone.matches(PHONE_REGEX);
    }

    public static boolean isPassportValid(String passport) {
         return (passport != null) && passport.matches(PASSPORT_REGEX);
    }

    public static boolean isNameValid(String name) {
         return (name != null) && name.matches(NAME_REGEX);
    }

    public static boolean isPasswordRepeatValid(String password, String passwordRepeat) {
         boolean resp = false;
         if (passwordRepeat != null && password != null) {
             resp = password.equals(passwordRepeat);
         }
         return resp;
    }

    public static void main(String[] args) {
        System.out.println(isPassportValid("Md3333333"));
        System.out.println(isNameValid("Sasha"));
        System.out.println(isPasswordValid("Aa12345678"));
    }
}
