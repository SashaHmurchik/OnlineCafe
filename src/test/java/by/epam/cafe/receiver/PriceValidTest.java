package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PriceValidTest {
    private String stringForValidation;

    public PriceValidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"0.5"},
                {"100.8"},
                {"10.8"},
                {"10.00"},
                {"25.4"},
                {"30.5"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertTrue(Validator.isPriceValid(stringForValidation));
    }

}
