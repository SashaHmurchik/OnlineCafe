package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.constant.SignUpAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.Validator;
import by.epam.cafe.receiver.impl.UserReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;
import by.epam.cafe.util.HashPassword;

public class ChangePasswordCommand extends AbstractCommand{
    private static final String LOGIN_PATH = ConfigurationManager.getProperty("path.page.login");
    private static final String CHANGE_PASS = ConfigurationManager.getProperty("path.page.changepass");

    private static final String ERROR_MESSAGE_PARAM = "errorMessage";
    private static final String NEW_PASS_ERROR_MESSAGE = "message.new.password.error";
    private static final String NEW_PASS_CONFIRM_ERROR_MESSAGE = "message.password.repeat.error";
    private static final String PASS_ERROR_MESSAGE = "message.password.error";
    private static final String STATUS = "statusInfo";
    private static final String SUCCESS_MESSAGE = "message.pass.change.success";

    public ChangePasswordCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        String newPassword = content.getRequestParameters().get(SignUpAtr.NEW_PASSWORD_PARAM)[0];
        String newPasswordConfirm = content.getRequestParameters().get(SignUpAtr.NEW_PASSWORD_REPEAT_PARAM)[0];
        String password = content.getRequestParameters().get(SignUpAtr.PASSWORD_PARAM)[0];

        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
        System.out.println(newPassword);
        if (!Validator.isPasswordValid(newPassword)) {
            content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                    MessageManager.getManager(locale).getMessage(NEW_PASS_ERROR_MESSAGE));
        } else if (!Validator.isPasswordRepeatValid(newPassword, newPasswordConfirm)) {
            content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                    MessageManager.getManager(locale).getMessage(NEW_PASS_CONFIRM_ERROR_MESSAGE));
        } else if (!HashPassword.md5Custom(password).equals(user.getPassword())) {
            content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                    MessageManager.getManager(locale).getMessage(PASS_ERROR_MESSAGE));
        } else {
            UserReceiverImpl userReceiver = (UserReceiverImpl)getReceiver();
            try {
                final boolean result1 = userReceiver.changePassword(user, newPassword);
                result = result1;
            } catch (ReceiverException e) {
                throw new CommandException("Problem during change password command ", e);
            }
        }

        if(result) {
            content.setSessionAttributes(STATUS, MessageManager.getManager(locale).getMessage(SUCCESS_MESSAGE));
        }
        return new RequestResult(LOGIN_PATH, NavigationType.REDIRECT);
    }
}
