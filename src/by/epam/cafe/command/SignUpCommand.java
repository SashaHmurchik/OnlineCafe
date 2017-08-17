package by.epam.cafe.command;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.constant.SignUpAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.receiver.Validator;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

import java.util.ArrayList;

public class SignUpCommand extends AbstractCommand {

    SignUpCommand(UserReceiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String email = (String)content.getRequestParameters().get(SignUpAtr.EMAIL_PARAM)[0];
        String passport = (String)content.getRequestParameters().get(SignUpAtr.PASSPOET_PARAM)[0];
        String password = (String)content.getRequestParameters().get(SignUpAtr.PASSWORD_PARAM)[0];
        System.out.println("!!!!!! - " + password);
        String repeatPassword = (String)content.getRequestParameters().get(SignUpAtr.REPEAT_PASSWORD_PARAM)[0];
        String name = (String)content.getRequestParameters().get(SignUpAtr.NAME_PARAM)[0];
        String surname = (String)content.getRequestParameters().get(SignUpAtr.SURNAME_PARAM)[0];
        String phoneNumber = (String)content.getRequestParameters().get(SignUpAtr.PHONE_PARAM)[0];
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
        if (locale == null) {
            locale = "EN";
        }

        content.getRequestAttributes().put("registration", new Boolean(false));
        UserReceiver userReceiver = (UserReceiver)getReceiver();

        try {
            if (!Validator.isEmailValid(email)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.emailerror"));
            } else if (userReceiver.isEmailExist(content)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.emailerrortwo"));
            } else if (!Validator.isSurnameValid(surname)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.surnameerror"));
            } else if (!Validator.isNameValid(name)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.nameerror"));
            } else if (!Validator.isPassportValid(passport)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.passporterror"));
            } else if (!Validator.isPhoneValid(phoneNumber)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.phoneerror"));
            } else if (!Validator.isPasswordValid(password)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.passworderror"));
            } else if (!Validator.isPasswordRepeatValid(password, repeatPassword)) {
                content.getRequestAttributes().put("errorSignUpMessage",
                        MessageManager.valueOf(locale).getMessage("message.passwordrepeaterror"));
            } else {
                userReceiver.signUpLogic(content);
            }

        } catch (ReceiverException e) {
            throw new CommandException("Problem during user sign up", e);
        }
        String page = null;
        RequestResult requestResult = null;

        if ((Boolean)content.getRequestAttributes().get("registration")) {
            requestResult = new RequestResult(ConfigurationManager.getProperty("path.page.login"), NavigationType.REDIRECT);
        } else {
            requestResult = new RequestResult(ConfigurationManager.getProperty("path.page.registration"), NavigationType.FORWARD);
        }

        return requestResult;
    }
}
