package by.epam.cafe.command.impl;


 import by.epam.cafe.command.AbstractCommand;
 import by.epam.cafe.constant.SessionAtr;
 import by.epam.cafe.content.NavigationType;
 import by.epam.cafe.content.RequestContent;
 import by.epam.cafe.content.RequestResult;
 import by.epam.cafe.entity.User;
 import by.epam.cafe.entity.Role;
 import by.epam.cafe.exception.CommandException;
 import by.epam.cafe.exception.ReceiverException;
 import by.epam.cafe.receiver.UserReceiver;
 import by.epam.cafe.resource.ConfigurationManager;
 import by.epam.cafe.resource.MessageManager;

public class LoginCommand extends AbstractCommand {
    private static final String FOODCORT_PATH = ConfigurationManager.getProperty("path.page.foodcorttwo");
    private static final String ADMINCORT_PATH = ConfigurationManager.getProperty("path.page.admincort");
    private static final String LOGIN_PATH = ConfigurationManager.getProperty("path.page.login");
    private static final String ERROR_LOGIN_PASS_ATR = "errorLoginPassMessage";
    private static final String AUTORIZATION_MARKER = "authorization";
    private static final String MESSAGE_LOGIN_ERROR = "message.login.error";


    public LoginCommand(UserReceiver receiver) {
        super(receiver);
    }

    public RequestResult execute(RequestContent content) throws CommandException{

        try {
            UserReceiver userReceiver = (UserReceiver)getReceiver();
            userReceiver.loginLogic(content);
        } catch (ReceiverException e) {
            throw new CommandException("Problem during user login", e);
        }

        RequestResult requestResult;

        if ((Boolean)content.getRequestAttributes().get(AUTORIZATION_MARKER)) {
            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            if(user.getRole() == Role.CLIENT) {
                requestResult = new RequestResult(FOODCORT_PATH, NavigationType.REDIRECT);
            } else {
                requestResult = new RequestResult(ADMINCORT_PATH, NavigationType.REDIRECT);
            }
        } else {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            content.setSessionAttributes(ERROR_LOGIN_PASS_ATR,
                MessageManager.getManager(locale).getMessage(MESSAGE_LOGIN_ERROR));
            requestResult = new RequestResult(LOGIN_PATH, NavigationType.REDIRECT);
        }
        return requestResult;
    }
}
