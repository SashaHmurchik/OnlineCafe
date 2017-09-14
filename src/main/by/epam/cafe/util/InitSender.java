package by.epam.cafe.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class InitSender {
    static Logger logger = LogManager.getLogger(InitSender.class);

    static final String RESOURCEBUNDLE_NAME = "resources/mail";
    static final String EMAIL = "mail.username";
    static final String PASSWORD = "mail.password";
    static final String AUTH = "mail.smtp.auth";
    static final String STARTTLS = "mail.smtp.starttls.enable";
    static final String HOST = "mail.smtp.host";
    static final String PORT = "mail.smtp.port";

    static ResourceBundle resourceBundle;
    static String eMail;
    static String password;
    static String auth;
    static String starttls;
    static String host;
    static String port;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle(RESOURCEBUNDLE_NAME);
            eMail = resourceBundle.getString(EMAIL);
            password = resourceBundle.getString(PASSWORD);
            auth = resourceBundle.getString(AUTH);
            starttls = resourceBundle.getString(STARTTLS);
            host = resourceBundle.getString(HOST);
            port = resourceBundle.getString(PORT);
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new RuntimeException("Exception during reading resourceBundle", e);
        }
    }
}
