package by.epam.cafe.command;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
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

    ShowDishCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        String page;

        try {
            DishReceiver dishReceiver = (DishReceiver)getReceiver();
            dishReceiver.showDish(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during user login", e);
        }


        RequestResult requestResult;
        /*
        RequestAttributeSaver.savedRequestAttributes = content.getRequestAttributes();
        */

        if ((List)content.getRequestAttributes().get("dishList") != null) {
            requestResult = new RequestResult(FOODCORT_PATH, NavigationType.FORWARD);
        } else {
            //content.getRequestAttributes().put("errorLoginPassMessage",
                   // MessageManager.valueOf(locale).getMessage("message.loginerror"));
            requestResult = new RequestResult(ConfigurationManager.getProperty("path.page.login"), NavigationType.FORWARD);
        }
        return requestResult;
    }
}
