package by.epam.cafe.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PictureNameValid {
    private String stringForValidation;

    public PictureNameValid(String stringForValidation) {
        this.stringForValidation = stringForValidation;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> stepUpStringValues() {
        return Arrays.asList(new Object[][]{
                {"picture.jpg"},
                {"picture.png"},
                {"picture.bmp"},
                {"picture.gif"},
                {"picture.pic.jpg"},
                {"picture_pic.jpg"}
        });
    }
    @Test
    public void isAmountValid() throws Exception {
        assertTrue(Validator.isPictureNameValid(stringForValidation));
    }
}