package by.epam.cafe.pool;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

class InitDB {
    static Logger logger = LogManager.getLogger(InitDB.class);

    static final String RESOURCEBUNDLE_NAME = "resources/dbconnection_config";
    static final String USER = "db_user";
    static final String PASSWORD = "db_password";
    static final String CONNECTION = "db_connection";
    static final String POOL_SOZE = "db_pool_size";

    static ResourceBundle resourceBundle;
    static String user;
    static String password;
    static String url;
    static int poolSize;

    static {
        try {
            resourceBundle = ResourceBundle.getBundle(RESOURCEBUNDLE_NAME);
            user = resourceBundle.getString(USER);
            password = resourceBundle.getString(PASSWORD);
            url = resourceBundle.getString(CONNECTION);
            poolSize = Integer.parseInt(resourceBundle.getString(POOL_SOZE));
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new RuntimeException("Exception during reading resourceBundle", e);
        }
    }
}
