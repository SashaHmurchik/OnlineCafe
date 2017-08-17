package by.epam.cafe.exception;

public class CommandException extends Exception {
    public CommandException() {
    }

    public CommandException(String s) {
        super(s);
    }

    public CommandException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CommandException(Throwable throwable) {
        super(throwable);
    }

    public CommandException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
