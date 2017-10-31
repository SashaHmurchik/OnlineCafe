//package by.epam.cafe.receiver;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import static org.junit.Assert.*;
//
//@RunWith(Parameterized.class)
//public class ValidatorTest {
//    private String stringForValidation;
//
//    public ValidatorTest(String stringForValidation) {
//        this.stringForValidation = stringForValidation;
//    }
//    @Parameterized.Parameters
//    public static Collection<Object[]> stepUpStringValues() {
//        return Arrays.asList(new Object[][]{
//                {"100 гр"},
//                {"0.5 л"},
//                {"100/200/20 гр"},
//                {"0.75 л"},
//                {"1.2 кг"},
//                {"1000/300/40/20 гр"}
//        });
//    }
//    @Test
//    public void isAmountValid() throws Exception {
//        assertTrue(Validator.isAmountValid(stringForValidation));
//    }
//
//}