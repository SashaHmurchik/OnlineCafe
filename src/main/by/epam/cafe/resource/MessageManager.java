package by.epam.cafe.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public enum MessageManager {
    EN( ResourceBundle.getBundle("resources/pagecontent", new Locale("en", "US"))),
    RU( ResourceBundle.getBundle("resources/pagecontent", new Locale("ru", "RU")));

    private static Logger logger = LogManager.getLogger(MessageManager.class);
    private ResourceBundle resourceBundle;

    MessageManager(ResourceBundle bundle) {
        //обработать ошибку получения ресурcбандла
        this.resourceBundle = bundle;
    }

    public static MessageManager getManager(String locale) {
        String result = "EN";
        if (locale.equals("ru_RU")) {
            result = "RU";
        } else {
            result = "EN";
        }
        return valueOf(result);
    }

    public String getMessage(String key) {
        String res = null;
        try {
            res = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new RuntimeException("Exception during reading resourceBundle", e);
        }
        return res;
    }
}

