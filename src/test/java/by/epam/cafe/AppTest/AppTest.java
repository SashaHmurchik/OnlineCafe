package by.epam.cafe.AppTest;

import by.epam.cafe.pool.ConnectionPoolTest;
import by.epam.cafe.receiver.*;
import by.epam.cafe.util.HashPasswordTest;
import by.epam.cafe.util.SenderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        ConnectionPoolTest.class,
        PassportValidTest.class,
        PassportValidTest2.class,
        PhoneValidTest.class,
        PhoneValidTest2.class,
        PictureNameValid.class,
        PictureNameValid2.class,
        PriceValidTest.class,
        PriceValidTest2.class,
        SiteValidTest.class,
        SiteValidTest2.class,
        ValidatorTest.class,
        ValidatorTest2.class,
        HashPasswordTest.class,
        SenderTest.class
        })

@RunWith(Suite.class)
public class AppTest {

}
