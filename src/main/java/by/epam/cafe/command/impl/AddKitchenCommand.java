package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.KitchenReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.Validator;
import by.epam.cafe.receiver.impl.KitchenReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

public class AddKitchenCommand extends AbstractCommand {
    private static final String NEW_KITCHEN_PATH = ConfigurationManager.getProperty("path.page.newkitchen");

    private static final String OPERATION_STATUS_POSITIVE = "addStatusPositive";
    private static final String OPERATION_STATUS_NEGATIVE = "addStatusNegative";
    private static final String SUCCESS = "message.operation.success";

    public AddKitchenCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {

        String kitchenName = requestContent.getRequestParameters().get(EntityAtr.KITCHEN_NAME_PARAM)[0];
        String kitchenEmail = requestContent.getRequestParameters().get(EntityAtr.KITCHEN_EMAIL_PARAM)[0];
        String kitchenSite = requestContent.getRequestParameters().get(EntityAtr.KITCHEN_SITE_PARAM)[0];
        String kitchenAddress = requestContent.getRequestParameters().get(EntityAtr.KITHEN_ADDRESS_PARAM)[0];
        String kitchenPhone = requestContent.getRequestParameters().get(EntityAtr.KITCHEN_PHONE_PARAM)[0];
        String picture = (String)requestContent.getRequestAttributes().get(EntityAtr.PICTURE_PARAM);

        String locale = (String)requestContent.getSessionAttributes().get(SessionAtr.LOCALE);
        if (picture == null) {
            requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.picture"));
        } else if(!Validator.isKitchenOrDiahNameValid(kitchenName)) {
            requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.kitchen.name"));
        } else if (!Validator.isEmailValid(kitchenEmail)) {
            requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.site"));
        } else if (!Validator.isSiteValid(kitchenSite)) {
            requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.email"));
        } else if (!Validator.isAddressValid(kitchenAddress)) {
            requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.address"));
        } else if (!Validator.isPhoneValid(kitchenPhone)) {
            requestContent.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.phone"));
        } else {
            boolean result = false;
            try {
                KitchenReceiverImpl kitchenReceiver = (KitchenReceiverImpl) getReceiver();
                result = kitchenReceiver.addKitchen(requestContent);
            } catch (ReceiverException e) {
                throw new CommandException(e.getMessage(), e);
            }

            if (result) {
                requestContent.setSessionAttributes(OPERATION_STATUS_POSITIVE, SUCCESS);
            }
        }
        return new RequestResult(NEW_KITCHEN_PATH, NavigationType.REDIRECT);
    }
}
