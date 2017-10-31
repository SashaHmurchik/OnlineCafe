package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
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

import java.util.List;

public class FindUserBySurnameCommand extends AbstractCommand {
    private static final String ADMINCORT_PATH = ConfigurationManager.getProperty("path.page.admincort");

    private static final String FIND_USER_STATUS = "findUserStatus";
    private static final String USER_NOT_FOUND_MESSAGE = "message.user.not.found";
    private static final String USER_LIST = "userList";
    private static final String PARAM_SURNAME = "surname";
    private static final String MESSAGE_SURNAME = "message.surname.error";
    private static final String PARAM_ALL_USERS = "allusers";

    public FindUserBySurnameCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        UserReceiverImpl userReceiver = (UserReceiverImpl)getReceiver();
        List<User> userList = null;
        String surname = content.getRequestParameters().get(PARAM_SURNAME)[0];
        RequestResult requestResult;
        if (!Validator.isSurnameValid(surname) && !surname.equals(PARAM_ALL_USERS)) {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            content.setRequestAttributes(FIND_USER_STATUS,
                    MessageManager.getManager(locale).getMessage(MESSAGE_SURNAME));
            requestResult = new RequestResult(ADMINCORT_PATH, NavigationType.FORWARD);
        } else {
            try {
                userList = userReceiver.findUserBySurname(content);
            } catch (ReceiverException e) {
                throw new CommandException("Problem during find user by surname logic", e);
            }
            if (!userList.isEmpty()) {
                content.setSessionAttributes(USER_LIST, userList);
                requestResult = new RequestResult(ADMINCORT_PATH, NavigationType.REDIRECT);
            } else {
                String locale = (String) content.getSessionAttributes().get(SessionAtr.LOCALE);
                content.setRequestAttributes(FIND_USER_STATUS,
                        MessageManager.getManager(locale).getMessage(USER_NOT_FOUND_MESSAGE));
                requestResult = new RequestResult(ADMINCORT_PATH, NavigationType.FORWARD);
            }
        }

        return requestResult;
    }
}
