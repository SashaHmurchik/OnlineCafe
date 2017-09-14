package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.RequestParam;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.OrderReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.util.DateParser;

import java.time.LocalDate;

public class RefuseTheOrderCommand extends AbstractCommand {
    private static final String ORDER_PATH = ConfigurationManager.getProperty("path.page.myorders.command");

    public RefuseTheOrderCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        LocalDate deliveryDate = DateParser.parseDate(content.getRequestParameters().get(RequestParam.DELIVERY_DATE)[0]);

        int compare = deliveryDate.compareTo(LocalDate.now());
        if (!(compare < 0)) {
            OrderReceiver orderReceiver = (OrderReceiverImpl)getReceiver();
            try {
                result = orderReceiver.refuseOrder(content);
            } catch (ReceiverException e) {
                throw new CommandException("Problem during refuse order", e);
            }
        } else {

        }
        RequestResult requestResult = null;
        if (result) {
            requestResult = new RequestResult(ORDER_PATH, NavigationType.REDIRECT);
        } else {
            requestResult = new RequestResult(ORDER_PATH, NavigationType.FORWARD);
        }
        return requestResult;
    }
}
