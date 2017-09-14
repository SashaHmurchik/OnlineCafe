package by.epam.cafe.exception;

public class ReceiverException extends Exception {
    public ReceiverException() {
    }

    public ReceiverException(String s) {
        super(s);
    }

    public ReceiverException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ReceiverException(Throwable throwable) {
        super(throwable);
    }

    public ReceiverException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
