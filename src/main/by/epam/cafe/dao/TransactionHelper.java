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

    /**
     * Pass a reference to a connection object for all
     * DAOs how participant in transaction.
     * And set auto commit to false
     * @param dao participants in the transaction
     * @param daos participants in the transaction
     */
    public void beginTransaction(AbstractDAO dao, AbstractDAO... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Problem when set auto commit false.");
        }

        dao.setConnection(connection);
        for (AbstractDAO d : daos) {
            d.setConnection(connection);
        }
    }

    /**
     * Ending transaction.
     * Set auto commit true, and return connection to pool
     */
    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Problem when set auto commit true.");
        }
        try {
            connection.close();
        } catch (SQLException e) {
           logger.log(Level.ERROR, "Can't return to pool connection.", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Transaction not commit.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Problem with roll bask transaction.", e);
        }
    }
}
