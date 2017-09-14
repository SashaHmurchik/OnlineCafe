package by.epam.cafe.command.impl;

import by.epam.cafe.bean.RegistrationBean;
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

public class EditUserCommand extends AbstractCommand {
    private static final String ACCOUNT_PATH = ConfigurationManager.getProperty("path.page.myaccout");

    private static final String ERROR_MESSAGE_PARAM = "errorMessage";
    private static final String MESSAGE_EMAIL_ERROR_PARAM = "message.email.error";
    private static final String MESSAGE_EMAIL_ERROR_TWO_PARAM = "message.email.error.two";
    private static final String MESSAGE_NAME_ERROR_PARAM = "message.name.error";
    private static final String MESSAGE_SURNAME_ERROR_PARAM ="message.surname.error";
    private static final String MESSAGE_PASSPORT_ERROR_PARAM = "message.passport.error";
    private static final String MESSAGE_PHONE_ERROR_PARAM = "message.phone.error";
    private static final String MESSAGE_PASSWORD_ERROR_PARAM  = "message.password.fail";


    public EditUserCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;

        String email = (String)content.getRequestParameters().get(SignUpAtr.EMAIL_PARAM)[0];
        String passport = (String)content.getRequestParameters().get(SignUpAtr.PASSPOET_PARAM)[0];
        String password = (String)content.getRequestParameters().get(SignUpAtr.PASSWORD_PARAM)[0];
        String name = (String)content.getRequestParameters().get(SignUpAtr.NAME_PARAM)[0];
        String surname = (String)content.getRequestParameters().get(SignUpAtr.SURNAME_PARAM)[0];
        String phoneNumber = (String)content.getRequestParameters().get(SignUpAtr.PHONE_PARAM)[0];
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);

        UserReceiverImpl userReceiver = (UserReceiverImpl)getReceiver();
        try {
            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            if (!Validator.isEmailValid(email)) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_EMAIL_ERROR_PARAM));
            } else if (userReceiver.isEmailExist(content) && !user.getMail().toUpperCase().equals(email.toUpperCase())) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_EMAIL_ERROR_TWO_PARAM));
            } else if (!Validator.isSurnameValid(surname)) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_SURNAME_ERROR_PARAM));
            } else if (!Validator.isNameValid(name)) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_NAME_ERROR_PARAM));
            } else if (!Validator.isPassportValid(passport)) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_PASSPORT_ERROR_PARAM));
            } else if (!Validator.isPhoneValid(phoneNumber)) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_PHONE_ERROR_PARAM));
            } else if (!HashPassword.md5Custom(password).equals(user.getPassword())) {
                content.setSessionAttributes(ERROR_MESSAGE_PARAM,
                        MessageManager.getManager(locale).getMessage(MESSAGE_PASSWORD_ERROR_PARAM));
            } else {
                result = userReceiver.editUser(content);
            }
        } catch (ReceiverException e) {
            throw new CommandException("Problem during edit user command ", e);
        }


        return new RequestResult(ACCOUNT_PATH, NavigationType.REDIRECT);
    }
}
