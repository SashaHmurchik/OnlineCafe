package by.epam.cafe.command.impl;

import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.receiver.Receiver;

public class LocaleCommand extends AbstractCommand {
    private static final String EN_LOCALE_MARKER = "EN";
    private static final String EN_LOCALE_ATR = "en_US";
    private static final String RU_LOCALE_ATR = "ru_RU";
    public LocaleCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws CommandException {
        String localeS = (String)requestContent.getRequestParameters().get(SessionAtr.LOCALE)[0];
        String locale = null;
        if(localeS.equals(EN_LOCALE_MARKER)) {
            locale = EN_LOCALE_ATR;
        } else {
            locale = RU_LOCALE_ATR;
        }
        requestContent.setSessionAttributes(SessionAtr.LOCALE, locale);

        String url = requestContent.getUrl();

        return new RequestResult(url, NavigationType.REDIRECT);
    }
}
