package by.epam.cafe.util;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SenderTest {
    private final static String MAIL_TO = "sasha.hmurchik@gmail.com";
    private final static String SUBJECT = "Test mailSender method";
    private final static String TEXT = "If you see this text - mailSender test is okey!";
    private static Sender sender;

    @BeforeClass
    public static void initSender() {
        sender = new Sender();
    }

   @AfterClass
   public static void deInitSender() {
        sender = null;
   }

    @Test
    public void send() throws Exception {
        sender.send(SUBJECT, TEXT, MAIL_TO);
    }

}