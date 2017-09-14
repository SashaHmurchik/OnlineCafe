package by.epam.cafe.command.factory;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.command.CommandMap;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.resource.MessageManager;


public class FactoryCommand {
    private static final String PARAM_COMMAND = "command";
    private static final String MESSAGE_WRONG_ACTION_PARAM = "message.wrongaction";
    private static final String WRONG_ACTION_PARAM = "wrongActiom";

    public AbstractCommand initCommand(RequestContent content) {
        AbstractCommand command = null;
        String commandName = null;

        try {
            commandName = content.getRequestParameters().get(PARAM_COMMAND)[0].toUpperCase();
            command = CommandMap.defineCommandType(commandName);
        } catch (IllegalArgumentException e) {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            content.setSessionAttributes(WRONG_ACTION_PARAM, commandName
                    + MessageManager.getManager(locale).getMessage(MESSAGE_WRONG_ACTION_PARAM));
        }
        return command;
    }
 }