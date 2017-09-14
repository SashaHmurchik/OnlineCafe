package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.Role;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.DishReceiver;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;

import java.util.List;

public class ShowDishCommand extends AbstractCommand {
    private static final String FOODCORT_PATH = ConfigurationManager.getProperty("path.page.foodcort");
    private static final String ADMIN_FOODCORT_PATH = ConfigurationManager.getProperty("path.page.adminfoodcort");
    private static final String LAST_PATH_ATR = "lastPath";

    private static final String DISH_LIST_PARAM = "dishList";
    public ShowDishCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String page;

        try {
            DishReceiver dishReceiver = (DishReceiver)getReceiver();
            dishReceiver.showDish(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during show dish command.", e);
        }

        RequestResult requestResult;

        if (content.getRequestAttributes().get(DISH_LIST_PARAM) != null) {
            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            if (user.getRole().equals(Role.ADMIN)) {
                requestResult = new RequestResult(ADMIN_FOODCORT_PATH, NavigationType.FORWARD);
            } else {
                requestResult = new RequestResult(FOODCORT_PATH, NavigationType.FORWARD);
            }
        } else {
            requestResult = new RequestResult(ConfigurationManager.getProperty("path.page.login"), NavigationType.FORWARD);
        }
        return requestResult;
    }
}
