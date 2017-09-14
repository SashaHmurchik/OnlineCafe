package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PassportValidTest {
    private String stringForValidation;

    public PassportValidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"MG3333333"},
                {"MC3428738"},
                {"MA0000000"},
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertTrue(Validator.isPassportValid(stringForValidation));
    }
}
