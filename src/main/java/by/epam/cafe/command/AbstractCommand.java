package by.epam.cafe.command;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;

public abstract class AbstractCommand {
    private Receiver receiver;
    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public abstract RequestResult execute(RequestContent requestContent) throws CommandException;
}
