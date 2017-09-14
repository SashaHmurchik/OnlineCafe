package by.epam.cafe.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class HashPasswordTest {
    private String expected;
    private String password;

    public HashPasswordTest(String expected, String password) {
        this.expected = expected;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"e10adc3949ba59abbe56e057f20f883e", "123456"},
                {"759c6be36aeaf6df7cc6f90e06ce3256", "gR55134125"}
        });
    }

    @Test
    public void md5Custom() throws Exception {
        String actual = HashPassword.md5Custom(password);
        assertEquals(expected, actual);
    }
}