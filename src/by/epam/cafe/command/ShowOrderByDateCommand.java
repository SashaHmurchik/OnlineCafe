package by.epam.cafe.command;

import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.dao.impl.OrderDaoImpl;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.OrderReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.util.DateParser;

import java.time.LocalDate;
import java.util.HashMap;

public class ShowOrderByDateCommand extends AbstractCommand {
    private static final String ORDERS_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    private static final String ORDERS_PARAM = "orderMap";
    private static final String ORDER_FIND_STATUS_PARAM = "orderFindStatus";
    private static final String ALL_ORDERS_VALUE = "allorders";

    public ShowOrderByDateCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String start = content.getRequestParameters().get(START_DATE_PARAM)[0];
        String end = content.getRequestParameters().get(END_DATE_PARAM)[0];

        LocalDate startDate = DateParser.parseDate(start);
        LocalDate endDate = DateParser.parseDate(end);

        HashMap<Order, HashMap<Dish, Integer>> orderMap = null;
        try {
            OrderReceiverImpl orderReceiver = (OrderReceiverImpl)getReceiver();
            orderMap = orderReceiver.findAllOrderByDeliveryDate(startDate, endDate);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during show order by date command", e);
        }

        if (orderMap.isEmpty()) {
            content.setRequestAttributes(ORDER_FIND_STATUS_PARAM, "no order fo this dates");
        }

        content.setSessionAttributes(ORDERS_PARAM, orderMap);

        return new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
    }
}
