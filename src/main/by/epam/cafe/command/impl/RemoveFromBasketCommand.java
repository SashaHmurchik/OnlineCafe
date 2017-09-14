package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.Iterator;

public class RemoveFromBasketCommand extends AbstractCommand {
    private static final String BASKET_PATH = ConfigurationManager.getProperty("path.page.basket");

    public RemoveFromBasketCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        try {
            UserReceiver userReceiver = (UserReceiver)getReceiver();
            result = userReceiver.removeFromBasket(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during add to basket", e);
        }

        if(result){
            content.setRequestAttributes("result", "dish removed");
        } else {
            content.setRequestAttributes("result", "dish NOT removed");
        }
        return new RequestResult(BASKET_PATH, NavigationType.REDIRECT);
    }
}
