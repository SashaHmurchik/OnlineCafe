package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class SiteValidTest {
    private String stringForValidation;

    public SiteValidTest(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"site.by"},
                {"https://ru.stackoverflow.com"},
                {"http://stackoverflow.com"},
                {"www.site.by"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertTrue(Validator.isSiteValid(stringForValidation));
    }

}
