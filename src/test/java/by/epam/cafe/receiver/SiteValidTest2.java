package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class SiteValidTest2 {
    private String stringForValidation;

    public SiteValidTest2(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"//site.by"},
                {"stackoverflow"},
                {"http:/stackoverflow.com"},
                {"www/stackoverflow.com"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertFalse(Validator.isSiteValid(stringForValidation));
    }

}
