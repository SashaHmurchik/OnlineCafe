package by.epam.cafe.command;

import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.Iterator;
import java.util.Set;

public class AddToBasketCommand extends AbstractCommand {
    private static final String FOODCORT_PATH = ConfigurationManager.getProperty("path.page.foodcort");

    AddToBasketCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent content) throws CommandException {
        boolean result = false;
        try {
            UserReceiver userReceiver = (UserReceiver)getReceiver();
            result = userReceiver.addToBasket(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during add to basket", e);
        }

        /*
        Iterator<String> keySet = RequestAttributeSaver.savedRequestAttributes.keySet().iterator();
        while(keySet.hasNext()) {
            String key = keySet.next();
            content.getRequestAttributes().put(key, RequestAttributeSaver.savedRequestAttributes.get(key));
        }
        */

        if(result) {
            content.setSessionAttributes("dishAddStatus", "dish add successful");
        } else {
            content.setSessionAttributes("dishAddStatus", "dish NOT add");
        }

        return new RequestResult(FOODCORT_PATH, NavigationType.REDIRECT);
    }
}
