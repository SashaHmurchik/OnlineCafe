package by.epam.cafe.command;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.receiver.Receiver;
import by.epam.cafe.resource.ConfigurationManager;

public class LocaleCommand extends AbstractCommand {
    LocaleCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        String localeS = (String)requestContent.getRequestParameters().get(SessionAtr.LOCALE)[0];
        String locale = null;
        if(localeS.equals("EN")) {
            locale = "en_US";
        } else {
            locale = "ru_RU";
        }
        requestContent.setSessionAttributes(SessionAtr.LOCALE, locale);

        String url = requestContent.getUrl();

        return new RequestResult(url, NavigationType.REDIRECT);
    }
}
