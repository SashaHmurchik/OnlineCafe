package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
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
import by.epam.cafe.resource.MessageManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ShowMyOrderCommand extends AbstractCommand {
    private static final String SHOW_MY_ORDERS_PATH = ConfigurationManager.getProperty("path.page.myorders");
    private static final String ORDER_MAP_PARAM = "orderMap";
    private static final String ORDER_FIND_STATUS = "orderFindStatus";
    public ShowMyOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        Map<Order, HashMap<Dish, Integer>> orderMap = null;
        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl) getReceiver();
            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            orderMap = orderReceiver.findAllOrderByUser(user);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during show my order command", e);
        }
        if (orderMap.isEmpty()) {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            content.setRequestAttributes(ORDER_FIND_STATUS,
                    MessageManager.getManager(locale).getMessage("message.user.have.not.order"));
        } else {
            content.setRequestAttributes(ORDER_MAP_PARAM, orderMap);
        }

        return new RequestResult(SHOW_MY_ORDERS_PATH, NavigationType.FORWARD);
    }
}
