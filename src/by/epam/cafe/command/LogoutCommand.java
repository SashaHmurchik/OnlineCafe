package by.epam.cafe.command;


import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.resource.ConfigurationManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LogoutCommand extends AbstractCommand {
    private static final String SESSION_INVALIDATE_MARCKER = "invalidate";

    LogoutCommand(UserReceiver receiver) {
        super(receiver);
    }

    public RequestResult execute(RequestContent content) {
        content.setSessionAttributes(SESSION_INVALIDATE_MARCKER, new Boolean(true));
        content.getSessionAttributes().remove("user");
        content.getSessionAttributes().remove("locale");
        
        RequestResult requestResult = new RequestResult(ConfigurationManager.getProperty("path.page.index"), NavigationType.REDIRECT);
        return requestResult;
    }
}
