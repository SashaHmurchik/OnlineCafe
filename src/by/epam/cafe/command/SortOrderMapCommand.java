package by.epam.cafe.command;

import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.HashMap;
import java.util.Map;

public class SortOrderMapCommand extends AbstractCommand {
    private static final String ORDER_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String ORDER_MAP_PARAM = "orderMap";

    public SortOrderMapCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        Map<Order, HashMap<Dish, Integer>> result = null;
        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl)getReceiver();
            result = orderReceiver.sorteOrders(requestContent);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during sort order map command", e);
        }

        requestContent.setSessionAttributes(ORDER_MAP_PARAM, result);

        return new RequestResult(ORDER_PATH, NavigationType.FORWARD);
    }

}
