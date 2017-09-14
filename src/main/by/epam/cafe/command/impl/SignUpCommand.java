package by.epam.cafe.command.impl;

import by.epam.cafe.bean.RegistrationBean;
import by.epam.cafe.command.AbstractCommand;
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
    public static final String LOGIN_PATH = ConfigurationManager.getProperty("path.page.login");
    public static final String REGISTRATION_PATH = ConfigurationManager.getProperty("path.page.registration");

    public static final String REGIST_BEAN = "registrBin";
    public SignUpCommand(UserReceiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String email = (String)content.getRequestParameters().get(SignUpAtr.EMAIL_PARAM)[0];
        String passport = (String)content.getRequestParameters().get(SignUpAtr.PASSPOET_PARAM)[0];
        String password = (String)content.getRequestParameters().get(SignUpAtr.PASSWORD_PARAM)[0];
        String repeatPassword = (String)content.getRequestParameters().get(SignUpAtr.REPEAT_PASSWORD_PARAM)[0];
        String name = (String)content.getRequestParameters().get(SignUpAtr.NAME_PARAM)[0];
        String surname = (String)content.getRequestParameters().get(SignUpAtr.SURNAME_PARAM)[0];
        String phoneNumber = (String)content.getRequestParameters().get(SignUpAtr.PHONE_PARAM)[0];
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);

        RegistrationBean registrationBean = new RegistrationBean();
        registrationBean.setEmail(email);
        registrationBean.setName(name);
        registrationBean.setSurname(surname);
        registrationBean.setPassport(passport);
        registrationBean.setPhone(phoneNumber);
        content.setSessionAttributes(REGIST_BEAN, registrationBean);

        content.setRequestAttributes("registration", false);
        UserReceiver userReceiver = (UserReceiver)getReceiver();

        try {
            if (!Validator.isEmailValid(email)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.email.error"));
            } else if (userReceiver.isEmailExist(content)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.email.error.two"));
            } else if (!Validator.isSurnameValid(surname)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.surname.error"));
            } else if (!Validator.isNameValid(name)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.name.error"));
            } else if (!Validator.isPassportValid(passport)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.passport.error"));
            } else if (!Validator.isPhoneValid(phoneNumber)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.phone.error"));
            } else if (!Validator.isPasswordValid(password)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.password.error"));
            } else if (!Validator.isPasswordRepeatValid(password, repeatPassword)) {
                content.setSessionAttributes("errorSignUpMessage",
                        MessageManager.getManager(locale).getMessage("message.password.repeat.error"));
            } else {
                userReceiver.signUpLogic(content);
            }

        } catch (ReceiverException e) {
            throw new CommandException("Problem during user sign up", e);
        }

        RequestResult requestResult = null;
        String answerPath = REGISTRATION_PATH;

        if ((Boolean)content.getRequestAttributes().get("registration")) {
            answerPath = LOGIN_PATH;
        }

        return new RequestResult(answerPath, NavigationType.REDIRECT);
    }
}
