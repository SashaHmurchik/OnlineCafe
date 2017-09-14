package by.epam.cafe.exception;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String s) {
        super(s);
    }

    public ConnectionPoolException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ConnectionPoolException(Throwable throwable) {
        super(throwable);
    }

    public ConnectionPoolException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
