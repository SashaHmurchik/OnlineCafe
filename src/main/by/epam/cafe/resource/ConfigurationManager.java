package by.epam.cafe.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigurationManager {
    private static Logger logger = LogManager.getLogger(MessageManager.class);

    private static ResourceBundle resourceBundle;

    private ConfigurationManager() { }

    public static String getProperty(String key) {
        String res = null;
        try {
            resourceBundle = ResourceBundle.getBundle("resources/config");
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "!!!!!Exception during reading resourceBundle", e);
            throw new RuntimeException("!!!!!Exception during reading resourceBundle", e);
        }
        try {
            res = resourceBundle.getString(key);
        } catch (MissingResourceException | ExceptionInInitializerError e) {
            logger.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new RuntimeException("Exception during reading resourceBundle", e);
        }
        return res;
    }
}
