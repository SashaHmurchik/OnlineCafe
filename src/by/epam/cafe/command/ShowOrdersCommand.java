package by.epam.cafe.command;

import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.OrderReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowOrdersCommand extends AbstractCommand {
    private static final String ORDERS_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String USER_ID_PARAM = "clientId";
    private static final String USER_LIST_PARAM = "userList";
    private static final String USER_ORDERS_PARAM = "orderMap";
    private static final String ORDER_FIND_STATUS_PARAM = "orderFindStatus";

    ShowOrdersCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String userId = content.getRequestParameters().get(USER_ID_PARAM)[0];

        List<User> userList = (List<User>)content.getSessionAttributes().get(USER_LIST_PARAM);
        List<User> findUser = new ArrayList<>();

        userList.stream().filter(o -> o.getId() == (Integer.valueOf(userId))).forEach(o -> findUser.add(o));

        HashMap<Order, HashMap<Dish, Integer>> orderMap = null;
        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl)getReceiver();
            orderMap = orderReceiver.findAllOrderByUser(findUser.get(0));
        } catch (ReceiverException e) {
            throw new CommandException("Problem during show order command", e);
        }

        if (orderMap.isEmpty()) {
            content.setRequestAttributes(ORDER_FIND_STATUS_PARAM, "user havn't orders");
        }

        content.setSessionAttributes(USER_ORDERS_PARAM, orderMap);

        return new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
    }
}
