package by.epam.cafe.command;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.HashMap;

public class ShowMyOrderCommand extends AbstractCommand {
    private static final String SHOW_MY_ORDERS_PATH = ConfigurationManager.getProperty("path.page.myorders");
    private static final String MY_ORDERS_PARAM = "myorders";

    ShowMyOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        HashMap<Order, HashMap<Dish, Integer>> orderMap = null;
        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl) getReceiver();
            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            orderMap = orderReceiver.findAllOrderByUser(user);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during show my order command", e);
        }
        if (orderMap.isEmpty()) {
            content.setRequestAttributes("orderFindStatus", "you have not orders");
        }
        content.setRequestAttributes(MY_ORDERS_PARAM ,orderMap);

        return new RequestResult(SHOW_MY_ORDERS_PATH, NavigationType.FORWARD);
    }
}
