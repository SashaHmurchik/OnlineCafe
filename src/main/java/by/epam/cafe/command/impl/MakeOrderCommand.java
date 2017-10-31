package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.RequestParam;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.receiver.Validator;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

public class MakeOrderCommand extends AbstractCommand {
    private static final String ORDER_PATH = ConfigurationManager.getProperty("path.page.myorders.command");
    private static final String BASKET_PATH = ConfigurationManager.getProperty("path.page.basket");
    private static final String MAKE_ORDER_RESULT = "makeOrderResult";
    private static final String SUCCESS = "message.operation.success";
    private static final String DATE_INVALID = "message.make.order.date.invalid";
    private static final String BASKET_EMPTY = "message.make.order.basket.empty";

    public MakeOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;

        String date = content.getRequestParameters().get(RequestParam.DELIVERY_DATE)[0];
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
        if (!Validator.isDateValid(date)) {
            content.setRequestAttributes(MAKE_ORDER_RESULT,
                    MessageManager.getManager(locale).getMessage(DATE_INVALID));
        } else if (user.getBasket().isEmpty()) {
            content.setRequestAttributes(MAKE_ORDER_RESULT,
                    MessageManager.getManager(locale).getMessage(BASKET_EMPTY));
        } else {
            try {
                OrderReceiverImpl orderReceiver = (OrderReceiverImpl) getReceiver();
                result = orderReceiver.makeNewOrder(content);
            } catch (ReceiverException e) {
                throw new CommandException("Problem during making new order", e);
            }
        }
        RequestResult requestResult = null;
        if (result) {
            content.setSessionAttributes(MAKE_ORDER_RESULT,
                    MessageManager.getManager(locale).getMessage(SUCCESS));
            requestResult = new RequestResult(ORDER_PATH, NavigationType.REDIRECT);
        } else {
            requestResult = new RequestResult(BASKET_PATH, NavigationType.FORWARD);
        }

        return requestResult;
    }
}
