package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;;
import by.epam.cafe.receiver.Validator;
import by.epam.cafe.receiver.impl.CategoryReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

public class AddCategoryCommand extends AbstractCommand {
    private static final String ADD_CATEGORY_PATH = ConfigurationManager.getProperty("path.page.newcategory");
    private static final String ADD_CATEGORY_STATUS_POSITIVE = "addCategoryStatusPositive";
    private static final String ADD_CATEGORY_STATUS_NEGATIVE = "addCategoryStatusNegative";
    private static final String MESSAGE_SUCCESS = "message.operation.success";

    public AddCategoryCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {

        String categoryName = requestContent.getRequestParameters().get(EntityAtr.CATEGORY_NAME)[0];
        String locale = (String) requestContent.getSessionAttributes().get(SessionAtr.LOCALE);

        if (!Validator.isKitchenOrDiahNameValid(categoryName)) {
            requestContent.setSessionAttributes(ADD_CATEGORY_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.category.name"));
        } else {

            boolean result = false;
            CategoryReceiverImpl categoryReceiver = (CategoryReceiverImpl) getReceiver();
            try {
                result = categoryReceiver.addNewCategory(requestContent);
            } catch (ReceiverException e) {
                throw new CommandException("Exception during add new category command", e);
            }

            if (result) {
                requestContent.setSessionAttributes(ADD_CATEGORY_STATUS_POSITIVE,
                        MessageManager.getManager(locale).getMessage(MESSAGE_SUCCESS));
            }
        }
        return new RequestResult(ADD_CATEGORY_PATH, NavigationType.REDIRECT);
    }
}
