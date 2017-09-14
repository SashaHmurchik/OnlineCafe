package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PictureNameValid2 {
    private String stringForValidation;

    public PictureNameValid2(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"picture.pdf"},
                {"picture.tiff"},
                {"ht picture.jpg"},
                {"picture/gif"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertFalse(Validator.isPictureNameValid(stringForValidation));
    }
}