package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PassportValidTest2 {
    private String stringForValidation;

    public PassportValidTest2(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"MG 3333333"},
                {"MC342873"},
                {"Ma0000000"},
                {"3333333"},
                {"MC"},
                {"MAA000000"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertFalse(Validator.isPassportValid(stringForValidation));
    }
}
