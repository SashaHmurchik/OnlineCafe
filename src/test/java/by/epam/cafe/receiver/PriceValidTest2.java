package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class PriceValidTest2 {
    private String stringForValidation;

    public PriceValidTest2(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"0.o4"},
                {"10000.8"},
                {"10,8"},
                {"70"},
                {"25.489"},
                {"30.5 р"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertFalse(Validator.isPriceValid(stringForValidation));
    }
}
