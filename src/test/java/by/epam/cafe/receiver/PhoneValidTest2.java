package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class PhoneValidTest2 {
    private String stringForValidation;

    public PhoneValidTest2(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"375293247320"},
                {"+ 375293247320"},
                {"+ 375 29 324 73 20"},
                {"-375 (29) 324-73-20"},
                {"324-73-20"},
                {"+ 375 (29) 32-73-20"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertFalse(Validator.isPhoneValid(stringForValidation));
    }
}
