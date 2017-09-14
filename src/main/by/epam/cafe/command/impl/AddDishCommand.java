package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.*;
import by.epam.cafe.receiver.impl.CategoryReceiverImpl;
import by.epam.cafe.receiver.impl.DishReceiverImpl;
import by.epam.cafe.receiver.impl.KitchenReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;
import com.sun.deploy.net.cookie.CookieUnavailableException;

public class AddDishCommand extends AbstractCommand {
    private static final String NEW_DISH_PATH = ConfigurationManager.getProperty("path.page.newdish");

    private static final String OPERATION_STATUS_POSITIVE = "addStatusPositive";
    private static final String OPERATION_STATUS_NEGATIVE = "addStatusNegative";
    private static final String SUCCESS = "message.operation.success";

    public AddDishCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        RequestResult requestResult = null;
        DishReceiver dishReceiver = (DishReceiverImpl)getReceiver();
        if(requestContent.getRequestParameters().get(EntityAtr.DISH_NAME_PARAM) == null) {
            try {
                CategoryReceiver categoryReceiver = new CategoryReceiverImpl();
                KitchenReceiver kitchenReceiver = new KitchenReceiverImpl();
                categoryReceiver.showCategory(requestContent);
                kitchenReceiver.showKitchen(requestContent);
                requestResult = new RequestResult(NEW_DISH_PATH, NavigationType.FORWARD);
            } catch (ReceiverException e) {
                throw new CommandException("Problem during add dish command", e);
            }
        } else {

            String dishPicture = (String)requestContent.getRequestAttributes().get(EntityAtr.PICTURE_PARAM);
            String dishName = requestContent.getRequestParameters().get(EntityAtr.DISH_NAME_PARAM)[0];
            String price = requestContent.getRequestParameters().get(EntityAtr.DISH_PRICE_PARAM)[0];
            String dishDescription = requestContent.getRequestParameters().get(EntityAtr.DISH_DESCRIPTION_PARAM)[0];
            String dishAmount = requestContent.getRequestParameters().get(EntityAtr.DISH_AMOUNT_PARAM)[0];

            String locale = (String)requestContent.getSessionAttributes().get(SessionAtr.LOCALE);
            if (dishPicture == null) {
                requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                        MessageManager.getManager(locale).getMessage("message.add.error.picture"));
            } else if (!Validator.isKitchenOrDiahNameValid(dishName)) {
                requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                        MessageManager.getManager(locale).getMessage("message.add.error.dishname"));
            } else if (!Validator.isPriceValid(price)) {
                requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                        MessageManager.getManager(locale).getMessage("message.add.error.price"));
            } else if (!Validator.isDescriptionValid(dishDescription)) {
                requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                        MessageManager.getManager(locale).getMessage("message.add.error.description"));
            } else if (!Validator.isAmountValid(dishAmount)) {
                requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                        MessageManager.getManager(locale).getMessage("message.add.error.amount"));
            } else {
                boolean result = false;
                try {
                    result = dishReceiver.addDish(requestContent);
                } catch (ReceiverException e) {
                    throw new CommandException("Problem during add dish command", e);
                }

                if (result) {
                    requestContent.setSessionAttributes(OPERATION_STATUS_POSITIVE,
                            MessageManager.getManager(locale).getMessage(SUCCESS));
                }
            }
        }
        return new RequestResult(NEW_DISH_PATH, NavigationType.REDIRECT);
    }
}
