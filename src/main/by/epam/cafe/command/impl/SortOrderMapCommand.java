package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.Role;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.HashMap;
import java.util.Map;

public class SortOrderMapCommand extends AbstractCommand {
    private static final String ADMIN_ORDER_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String CLIENT_ORDER_PATH = ConfigurationManager.getProperty("path.page.myorders");
    private static final String ORDER_MAP_PARAM = "orderMap";

    public SortOrderMapCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) {
        Map<Order, HashMap<Dish, Integer>> result = null;

        OrderReceiverImpl orderReceiver = (OrderReceiverImpl)getReceiver();
        result = orderReceiver.sorteOrders(requestContent);

        requestContent.setSessionAttributes(ORDER_MAP_PARAM, result);
        RequestResult requestResult = null;
        User user = (User)requestContent.getSessionAttributes().get(SessionAtr.USER);
        if (user.getRole().equals(Role.CLIENT)) {
            requestResult = new RequestResult(CLIENT_ORDER_PATH, NavigationType.FORWARD);
        } else {
            requestResult = new RequestResult(ADMIN_ORDER_PATH, NavigationType.FORWARD);
        }
        return requestResult;
    }

}
