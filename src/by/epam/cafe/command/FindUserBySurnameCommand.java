package by.epam.cafe.command;

import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.impl.UserReceiverImpl;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.List;

public class FindUserBySurnameCommand extends AbstractCommand {
    private static final String ADMINCORT_PATH = ConfigurationManager.getProperty("path.page.admincort");

    FindUserBySurnameCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        UserReceiverImpl userReceiver = (UserReceiverImpl)getReceiver();
        List<User> userList = null;
        try {
            userList = userReceiver.findUserBySurname(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during find user by surname logic", e);
        }

        if (userList.isEmpty()) {
            content.setRequestAttributes("findUserStatus", "users NOT found");
        }
        content.setSessionAttributes("userList", userList);

        return new RequestResult(ADMINCORT_PATH, NavigationType.FORWARD);
    }
}
