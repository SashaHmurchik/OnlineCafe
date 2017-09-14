package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.CategoryReceiver;
import by.epam.cafe.receiver.DishReceiver;
import by.epam.cafe.receiver.KitchenReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.CategoryReceiverImpl;
import by.epam.cafe.receiver.impl.DishReceiverImpl;
import by.epam.cafe.receiver.impl.KitchenReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

public class UpdateArchiveStatusCommand extends AbstractCommand {
    private static final String ADMIN_FOODCORT_PATH = ConfigurationManager.getProperty("path.page.adminfoodcort");
    private static final String ADMIN_CATEGORYCORT_PATH = ConfigurationManager.getProperty("path.page.admin.categorycort");
    private static final String ADMIN_KITCHENCORT_PATH = ConfigurationManager.getProperty("path.page.admin.kitchencort");
    private static String path= null;

    private static final String SUCCESS_MESSAGE = "message.operation.success";
    private static final String CHANGE_ARCHIVE_STATUS_RESULT = "changeArchiveStatusResult";

    private static final String MARCKER_PARAM = "archive_marker";
    private static final String DISH_MARCKER_VALUE = "dish_archive";
    private static final String CATEGORY_MARCKER_VALUE = "category_archive";
    private static final String KITCHEN_MARCKER_VALUE = "kitchen_archive";

    public UpdateArchiveStatusCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        boolean result = false;

        try {
            String marker = requestContent.getRequestParameters().get(MARCKER_PARAM)[0];
            switch (marker) {
                case DISH_MARCKER_VALUE :
                    DishReceiver dishReceiver = (DishReceiverImpl)getReceiver();
                    result = dishReceiver.updateArchiveStatus(requestContent);
                    path = ADMIN_FOODCORT_PATH;
                    break;
                case CATEGORY_MARCKER_VALUE :
                    CategoryReceiver categoryReceiver = new CategoryReceiverImpl();
                    result = categoryReceiver.updateCategoryArchiveStatus(requestContent);
                    path = ADMIN_CATEGORYCORT_PATH;
                    break;
                case KITCHEN_MARCKER_VALUE :
                    KitchenReceiver kitchenReceiver = new KitchenReceiverImpl();
                    result = kitchenReceiver.updateKitchenArchiveStatus(requestContent);
                    path = ADMIN_KITCHENCORT_PATH;
                    break;
            }

        } catch (ReceiverException e) {
            throw new CommandException("Problem during update archive status command.", e);
        }
        RequestResult requestResult = null;

        if (result) {
            String locale = (String)requestContent.getSessionAttributes().get(SessionAtr.LOCALE);
            requestContent.setSessionAttributes(CHANGE_ARCHIVE_STATUS_RESULT, MessageManager.getManager(locale).getMessage(SUCCESS_MESSAGE));
            requestResult = new RequestResult(path, NavigationType.REDIRECT);
        } else {
            requestResult = new RequestResult(path, NavigationType.FORWARD);
        }
        return requestResult;
    }
}
