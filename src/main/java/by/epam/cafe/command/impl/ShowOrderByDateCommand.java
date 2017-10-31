package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
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
import by.epam.cafe.receiver.Validator;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;
import by.epam.cafe.util.DateParser;

import java.time.LocalDate;
import java.util.HashMap;

public class ShowOrderByDateCommand extends AbstractCommand {
    private static final String ORDERS_PATH = ConfigurationManager.getProperty("path.page.orders");
    private static final String START_DATE_PARAM = "startDate";
    private static final String END_DATE_PARAM = "endDate";
    private static final String ORDER_PARAM = "orderMap";
    private static final String ORDER_FIND_STATUS_PARAM = "orderFindStatus";
    private static final String NOT_VALID = "message.not.valid.date";
    private static final String NO_ORDERS_MESSAGE = "message.order.no.orders";


    public ShowOrderByDateCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String start = content.getRequestParameters().get(START_DATE_PARAM)[0];
        String end = content.getRequestParameters().get(END_DATE_PARAM)[0];
        RequestResult requestResult = null;
        if (!Validator.isDateValid(start ) && Validator.isDateValid(end)) {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            content.setRequestAttributes(ORDER_FIND_STATUS_PARAM,
                    MessageManager.getManager(locale).getMessage(NOT_VALID));
            requestResult = new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
        } else if(!Validator.isTimePeriodValid(DateParser.parseDate(start), DateParser.parseDate(end))) {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            content.setRequestAttributes(ORDER_FIND_STATUS_PARAM,
                    MessageManager.getManager(locale).getMessage(NOT_VALID));
            requestResult = new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
        } else {
            LocalDate startDate = DateParser.parseDate(start);
            LocalDate endDate = DateParser.parseDate(end);

            HashMap<Order, HashMap<Dish, Integer>> orderMap = null;
            try {
                OrderReceiverImpl orderReceiver = (OrderReceiverImpl) getReceiver();
                orderMap = orderReceiver.findAllOrderByDeliveryDate(startDate, endDate);
            } catch (ReceiverException e) {
                throw new CommandException("Problem during show order by date command", e);
            }

            if (orderMap.isEmpty()) {
                String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
                content.setRequestAttributes(ORDER_FIND_STATUS_PARAM,
                        MessageManager.getManager(locale).getMessage(NO_ORDERS_MESSAGE));
                requestResult = new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
            } else {
                content.setSessionAttributes(ORDER_PARAM, orderMap);
                requestResult = new RequestResult(ORDERS_PATH, NavigationType.REDIRECT);
            }


        }
        return requestResult;
    }
}
