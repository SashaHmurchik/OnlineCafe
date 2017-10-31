package by.epam.cafe.command.impl;


import by.epam.cafe.command.AbstractCommand;
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
    private static final String INDEX_PATH = "path.page.index";

    public LogoutCommand(UserReceiver receiver) {
        super(receiver);
    }

    public RequestResult execute(RequestContent content) {
        content.sessionInvalidate();
        content.setSessionAttributes(SESSION_INVALIDATE_MARCKER, new Boolean(true));

        return new RequestResult(ConfigurationManager.getProperty(INDEX_PATH), NavigationType.REDIRECT);
    }
}
