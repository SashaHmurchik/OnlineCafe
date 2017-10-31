package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.receiver.impl.UserReceiverImpl;

public class UpdateLoyaltyPiointCommand extends AbstractCommand {
    public UpdateLoyaltyPiointCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        boolean result = false;
        UserReceiver userReceiver = (UserReceiverImpl)getReceiver();
        try {
            result = userReceiver.updateLoyaltyPointByUserId(requestContent);

        } catch (ReceiverException e) {
            throw new CommandException("Exception during udate loyalty point command", e);
        }
        RequestResult requestResult = null;
        if (result) {
            requestResult = new RequestResult(requestContent.getUrl(), NavigationType.REDIRECT);
        } else {
            requestResult = new RequestResult(requestContent.getUrl(), NavigationType.FORWARD);
        }
        return requestResult;
    }
}
