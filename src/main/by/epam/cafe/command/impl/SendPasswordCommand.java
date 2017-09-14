package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.UserReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

public class SendPasswordCommand extends AbstractCommand {
    private static final String LOGIN_PATH = ConfigurationManager.getProperty("path.page.login");
    private static final String RECOVERY_PATH = ConfigurationManager.getProperty("path.page.recovery");

    private static final String STATUS = "statusInfo";

    public SendPasswordCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        try {
            UserReceiverImpl userReceiver = (UserReceiverImpl)getReceiver();
            result = userReceiver.sendPassword(content);
        } catch (ReceiverException e) {
            throw new CommandException(e.getMessage(), e);
        }
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
        RequestResult requestResult = null;
        if(result) {
            content.setSessionAttributes(STATUS, MessageManager.getManager(locale).getMessage("message.pass.recovery.success"));
            requestResult = new RequestResult(LOGIN_PATH, NavigationType.REDIRECT);
        } else {
            content.setSessionAttributes(STATUS, MessageManager.getManager(locale).getMessage("message.pass.recovery.not.success"));
            requestResult = new RequestResult(RECOVERY_PATH, NavigationType.REDIRECT);
        }
        return requestResult;
    }
}
