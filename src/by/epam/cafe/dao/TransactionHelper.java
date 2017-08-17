package by.epam.cafe.dao;

import by.epam.cafe.pool.ConnectionPool;
import by.epam.cafe.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionHelper {
    private static final Logger logger = LogManager.getLogger(TransactionHelper.class);

    private ProxyConnection connection = ConnectionPool.getInstance().getConnection();

    public void beginTransaction(AbstractDAO dao, AbstractDAO... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Problem when set auto commit false");
        }

        dao.setConnection(connection);
        for (AbstractDAO d : daos) {
            d.setConnection(connection);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Problem when set auto commit true");
        }
        try {
            connection.close();
        } catch (SQLException e) {
           logger.log(Level.ERROR, "Can't return to pool connection", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Transaction not commited", e);
        }
    }
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Problem whith rallbask transaction", e);
        }
    }
}
