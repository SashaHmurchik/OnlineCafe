package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ValidatorTest2 {
    private String stringForValidation;

    public ValidatorTest2(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"100гр"},
                {" л"},
                {"100/20020 гр"},
                {"0,75 л"},
                {"1.2 килограмм"},
                {"/1000/300/40/20/50/80 гр"},
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertFalse(Validator.isAmountValid(stringForValidation));
    }

}