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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayForOrderByAdminCommand extends AbstractCommand {
    private static final String ORDERS_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String USER_LIST_PARAM = "userList";
    private static final String USER_ORDERS_PARAM = "orderMap";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final boolean PAID_STATUS = true;

    PayForOrderByAdminCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        boolean result = false;

        String orderId = requestContent.getRequestParameters().get(ORDER_ID_PARAM)[0];
        HashMap<Order, HashMap<Dish, Integer>> orderMap = (HashMap<Order, HashMap<Dish, Integer>>)requestContent.getSessionAttributes().get(USER_ORDERS_PARAM);
        List<Order> userOrder = new ArrayList<>();

        orderMap.keySet().stream().filter(o -> o.getId() == Integer.parseInt(orderId)).forEach(o -> userOrder.add(o));

        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl)getReceiver();
            result = orderReceiver.updatePaidStatus(userOrder.get(0), PAID_STATUS);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during update paid status command", e);
        }
        if (result) {
            requestContent.setSessionAttributes("paidCommandStatus", "you paid by order");
        } else {
            requestContent.setSessionAttributes("paidCommandStatus", "you NOT paid by order");
        }

        return new RequestResult(ORDERS_PATH, NavigationType.REDIRECT);
    }
}
