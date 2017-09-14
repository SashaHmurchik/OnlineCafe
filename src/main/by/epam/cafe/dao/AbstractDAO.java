package by.epam.cafe.dao;


import by.epam.cafe.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDAO {
    private static Logger logger = LogManager.getLogger(AbstractDAO.class);

    protected ProxyConnection connection;

    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Can't close statement", e);
        }
    }

    void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }
}
