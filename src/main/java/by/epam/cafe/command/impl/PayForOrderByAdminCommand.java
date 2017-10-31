package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
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
import by.epam.cafe.resource.MessageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayForOrderByAdminCommand extends AbstractCommand {
    private static final String ORDERS_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String USER_LIST_PARAM = "userList";
    private static final String USER_ORDERS_PARAM = "orderMap";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String PAID_COMMAND_STATUS = "paidCommandStatus";
    private static final boolean PAID_STATUS = true;

    public PayForOrderByAdminCommand(Receiver receiver) {
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
        String locale = (String)requestContent.getSessionAttributes().get(SessionAtr.LOCALE);
        if (result) {
            requestContent.setSessionAttributes(PAID_COMMAND_STATUS,
                    MessageManager.getManager(locale).getMessage("label.order.paid"));
        } else {
            requestContent.setSessionAttributes(PAID_COMMAND_STATUS,
                    MessageManager.getManager(locale).getMessage("label.order.notpaid"));
        }

        return new RequestResult(ORDERS_PATH, NavigationType.REDIRECT);
    }
}
