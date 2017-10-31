package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.resource.MessageManager;



public class AddToBasketCommand extends AbstractCommand {

    private static final String ADD_STATUS = "dishAddStatus";
    private static final String SUCCESS_MESSAGE = "message.operation.success";
    private static final String NOT_SUCCESS_MESSAGE = "message.operation.not.success";

    public AddToBasketCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        try {
            UserReceiver userReceiver = (UserReceiver)getReceiver();
            result = userReceiver.addToBasket(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during add to basket", e);
        }
        RequestResult requestResult = null;

        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);

        String nextPath = content.getUrl();

        if(!result) {
            content.setSessionAttributes(ADD_STATUS,
                    MessageManager.getManager(locale).getMessage(NOT_SUCCESS_MESSAGE));
        } else {
            content.setSessionAttributes(ADD_STATUS,
                    MessageManager.getManager(locale).getMessage(SUCCESS_MESSAGE));
        }

        return new RequestResult(nextPath, NavigationType.REDIRECT);
    }
}
