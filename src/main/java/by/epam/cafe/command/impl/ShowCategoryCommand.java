package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.CategoryReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.CategoryReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

public class ShowCategoryCommand extends AbstractCommand {
    private static final String CATEGORYCORT_PATH = ConfigurationManager.getProperty("path.page.admin.categorycort");

    public ShowCategoryCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        CategoryReceiver categoryReceiver = (CategoryReceiverImpl)getReceiver();
        try {
            categoryReceiver.showCategory(requestContent);
        } catch(ReceiverException e) {
            throw new CommandException("Problem during show category command.",e);
        }
        return new RequestResult(CATEGORYCORT_PATH, NavigationType.FORWARD);
    }
}
