package by.epam.cafe.pool;

import by.epam.cafe.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionCreator {

    /**
     * Method for create ProxyConnection
     */
    static ProxyConnection createConnection() throws ConnectionPoolException {
        try {
            String url = InitDB.url;
            String user = InitDB.user;
            String pass = InitDB.password;
            Connection connection = DriverManager.getConnection(url, user, pass);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Connection creator error" , e);
        }
    }
}
