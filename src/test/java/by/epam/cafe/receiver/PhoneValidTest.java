package by.epam.cafe.receiver;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneValidTest {
    private String phone;

    public PhoneValidTest() {
        phone = "+375 (29) 324-73-20";
    }

    @Test
    public void isPhoneValid() throws Exception {
        assertTrue(Validator.isPhoneValid(phone));
    }
}
