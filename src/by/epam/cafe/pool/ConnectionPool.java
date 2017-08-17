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
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean instanceIsCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;

    private BlockingQueue<ProxyConnection> connectionQueue;

    private ConnectionPool() {
        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(InitDB.poolSize);
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
                System.out.println("2!!!!!!!!!!!!!");
                try {
                    ProxyConnection connection = ConnectionCreator.createConnection();
                    connectionQueue.offer(connection);
                } catch (ConnectionPoolException e) {
                    //logger.error;
                }
            }
            size = connectionQueue.size();
            if (size <= InitDB.poolSize/2) {
                logger.log(Level.ERROR, "There are not enough connections for the correct work of the application." + size + " connections");
                throw new RuntimeException("There are not enough connections for the correct work of the application." + size + " connections");
            }
        }
    }

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

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch(InterruptedException e) {
            logger.log(Level.ERROR, "Exception during getting connection", e);
        }
        return connection;
    }

    public void terminatePool() {
        try {
            for (int i = 0; i < connectionQueue.size(); i++) {
                connectionQueue.take().closeConnection();
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Exception during pool termination", e);
        }

        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            //logger.error
        }
    }

    void releaseConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }
}
