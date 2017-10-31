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

public class UpdateKitchenCommand extends AbstractCommand {
    private static final String KITCHEN_PATH = ConfigurationManager.getProperty("path.page.admin.kitchencort");

    private static final String OPERATION_STATUS_POSITIVE = "addStatusPositive";
    private static final String OPERATION_STATUS_NEGATIVE = "addStatusNegative";

    public UpdateKitchenCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String kitchenAddress = content.getRequestParameters().get(EntityAtr.KITHEN_ADDRESS_PARAM)[0];
        String kitchenEmail = content.getRequestParameters().get(EntityAtr.KITCHEN_EMAIL_PARAM)[0];
        String kitchenPhone = content.getRequestParameters().get(EntityAtr.KITCHEN_PHONE_PARAM)[0];
        String kitchenSite = content.getRequestParameters().get(EntityAtr.KITCHEN_SITE_PARAM)[0];

        boolean result = false;
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
        if (!Validator.isEmailValid(kitchenEmail)) {
            content.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.site"));
        } else if (!Validator.isSiteValid(kitchenSite)) {
            content.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.email"));
        } else if (!Validator.isAddressValid(kitchenAddress)) {
            content.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.address"));
        } else if (!Validator.isPhoneValid(kitchenPhone)) {
            content.setSessionAttributes(OPERATION_STATUS_NEGATIVE,
                    MessageManager.getManager(locale).getMessage("message.add.error.phone"));
        } else {
            KitchenReceiver kitchenReceiver = (KitchenReceiverImpl)getReceiver();
            try {
                result = kitchenReceiver.updateKitchen(content);
            } catch (ReceiverException e) {
                throw new CommandException("Exception during udate kitchen command", e);
            }
        }
        RequestResult requestResult = null;
        if (result) {
            requestResult = new RequestResult(KITCHEN_PATH, NavigationType.REDIRECT);
        } else {
            requestResult = new RequestResult(KITCHEN_PATH, NavigationType.FORWARD);
        }
        return requestResult;
    }
}
