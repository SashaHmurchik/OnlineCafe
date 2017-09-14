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

public class DeliverOrderCommand extends AbstractCommand {
    private static final String ORDERS_PATH = ConfigurationManager.getProperty("path.page.orders");

    private static final String USER_ORDERS_PARAM = "orderMap";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final boolean DELIVERY_STATUS = true;
    private static final String DELIVERY_COMMAND_STATUS = "deliveryCommandStatus";
    private static final String PAID_STATUS = "paidStatus";

    public DeliverOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        boolean result = false;

        String orderId = requestContent.getRequestParameters().get(ORDER_ID_PARAM)[0];
        HashMap<Order, HashMap<Dish, Integer>> orderMap = (HashMap<Order, HashMap<Dish, Integer>>)requestContent.getSessionAttributes().get(USER_ORDERS_PARAM);
        List<Order> userOrder = new ArrayList<>();

        orderMap.keySet().stream().filter(o -> o.getId() == Integer.parseInt(orderId)).forEach(o -> userOrder.add(o));
        String locale = (String)requestContent.getSessionAttributes().get(SessionAtr.LOCALE);
        if (userOrder.get(0).getPaid()) {
            try {
                OrderReceiverImpl orderReceiver = (OrderReceiverImpl) getReceiver();
                result = orderReceiver.updateDeliveryStatus(userOrder.get(0), DELIVERY_STATUS);
            } catch (ReceiverException e) {
                throw new CommandException("Problem during update paid status command", e);
            }
        } else {
            requestContent.setSessionAttributes(PAID_STATUS,
                    MessageManager.getManager(locale).getMessage("message.order.not.paid"));
        }

        if (result) {
            requestContent.setSessionAttributes(DELIVERY_COMMAND_STATUS,
                    MessageManager.getManager(locale).getMessage("message.order.delivered"));
        } else {
            requestContent.setSessionAttributes(DELIVERY_COMMAND_STATUS,
                    MessageManager.getManager(locale).getMessage("message.order.not.delivered"));
        }

        return new RequestResult(ORDERS_PATH, NavigationType.REDIRECT);
    }
}
