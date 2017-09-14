package by.epam.cafe.pool;


import by.epam.cafe.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    /**
     * Logger instance
     */
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    /**
     * ReentrantLock for lock access to creation new instance ConnectionPool
     */
    private static Lock lock = new ReentrantLock();
    /**
     * AtomicBoolean value for checking if the instance already exists
     */
    private static AtomicBoolean instanceIsCreated = new AtomicBoolean(false);
    /**
     * Instance of the ConnectionPool
     */
    private static ConnectionPool instance;

    /**
     * ArrayBlockingQueue for storage ProxyConnection objects
     */
    private BlockingQueue<ProxyConnection> connectionQueue;

    /**
     * The only method to get ConnectionPool instance
     * @return instance of ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (!instanceIsCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceIsCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * private constructor for creation only one instance of ConnectionPool
     */
    private ConnectionPool() {
        initConnectionPool();
    }

    /**
     * Method for initializing instance of ConnectionPool
     */
    private void initConnectionPool() {
        connectionQueue = new ArrayBlockingQueue<>(InitDB.poolSize);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch(SQLException e) {
            logger.log(Level.FATAL, "JDBC driver not registered", e);
            throw new RuntimeException("JDBC driver not registered", e);
        }

        for (int i = 0; i < InitDB.poolSize; i++) {
            try {
                ProxyConnection connection = ConnectionCreator.createConnection();
                connectionQueue.offer(connection);
            } catch (ConnectionPoolException e) {
                logger.log(Level.FATAL, "Connection not Created", e );
                throw new RuntimeException("Connection not Created", e);
            }
        }

        int size = connectionQueue.size();

        if (size != InitDB.poolSize) {
            for (int i = 0; i < size; i++) {
                try {
                    ProxyConnection connection = ConnectionCreator.createConnection();
                    connectionQueue.offer(connection);
                } catch (ConnectionPoolException e) {
                    logger.log(Level.ERROR, "Creation connection error");
                }
            }

            size = connectionQueue.size();
            if (size <= InitDB.poolSize/2) {
                logger.log(Level.ERROR, "There are not enough connections for the correct work of the application." + size + " connections");
                throw new RuntimeException("There are not enough connections for the correct work of the application." + size + " connections");
            }
        }
    }

    /**
     * Method for get ConnectionPool size
     */
    public int getSize() {
        return connectionQueue.size();
    }

    /**
     * Method for getting the ProxyConnection from ConnectionPool
     * @return ProxyConnection instance
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch(InterruptedException e) {
            logger.log(Level.ERROR, "Exception during getting connection", e);
        }
        return connection;
    }

    /**
     * Close all ProxyConnections
     */
    public void terminatePool() {

            for (int i = 0; i < connectionQueue.size(); i++) {
                try {
                    connectionQueue.take().closeConnection();
                } catch (SQLException | InterruptedException e) {
                    logger.log(Level.ERROR, "Exception during pool termination", e);
                }
            }
        deregisterAllDrivers();
    }

    /**
     * Derigister all Drivers
     */
    private void deregisterAllDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Deregister driver error");
        }
    }

    void releaseConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }
}
