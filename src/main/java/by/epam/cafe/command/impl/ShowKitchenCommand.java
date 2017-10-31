package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.CategoryReceiver;
import by.epam.cafe.receiver.KitchenReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.CategoryReceiverImpl;
import by.epam.cafe.receiver.impl.KitchenReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

public class ShowKitchenCommand extends AbstractCommand {
    private static final String KITCHENORT_PATH = ConfigurationManager.getProperty("path.page.admin.kitchencort");

    public ShowKitchenCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        KitchenReceiver kitchenReceiver = (KitchenReceiverImpl)getReceiver();
        try {
            kitchenReceiver.showKitchen(requestContent);
        } catch(ReceiverException e) {
            throw new CommandException("Problem during show kitchen command.",e);
        }
        return new RequestResult(KITCHENORT_PATH, NavigationType.FORWARD);
    }
}
