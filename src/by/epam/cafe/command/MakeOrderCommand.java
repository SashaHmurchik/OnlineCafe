package by.epam.cafe.command;

import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

public class MakeOrderCommand extends AbstractCommand {
    private static final String BASKET_PATH = ConfigurationManager.getProperty("path.page.basket");

    MakeOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl)getReceiver();
            result = orderReceiver.makeNewOrder(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during making new order", e);
        }

        if (result) {
            content.setRequestAttributes("ordercomplite", "order add successful");
        } else {
            content.setRequestAttributes("ordercomplite", "order NOT add");
        }

        return new RequestResult(BASKET_PATH, NavigationType.REDIRECT);
    }
}
