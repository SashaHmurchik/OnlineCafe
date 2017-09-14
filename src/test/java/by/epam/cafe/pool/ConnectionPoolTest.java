package by.epam.cafe.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionPoolTest {
    private int expectedSize;

    public ConnectionPoolTest() {
        expectedSize = 6;
    }

    @Test
    public void getInstanceSize() throws Exception {
        int actual = ConnectionPool.getInstance().getSize();
        assertEquals(expectedSize, actual);
    }

    @Test
    public void getInstance() throws Exception {
        assertNotNull(ConnectionPool.getInstance());
    }

    @Test
    public void getConnection() throws Exception {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        assertNotNull(connection);
        connection.close();
    }

}