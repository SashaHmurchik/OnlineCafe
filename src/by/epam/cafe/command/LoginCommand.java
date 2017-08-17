package by.epam.cafe.command;


 import by.epam.cafe.constant.SessionAtr;
 import by.epam.cafe.content.NavigationType;
 import by.epam.cafe.content.RequestContent;
 import by.epam.cafe.content.RequestResult;
 import by.epam.cafe.entity.User;
 import by.epam.cafe.entity.charackter.Role;
 import by.epam.cafe.exception.CommandException;
 import by.epam.cafe.exception.ReceiverException;
 import by.epam.cafe.receiver.UserReceiver;
 import by.epam.cafe.resource.ConfigurationManager;
 import by.epam.cafe.resource.MessageManager;

public class LoginCommand extends AbstractCommand {
    private static final String FOODCORT_PATH = ConfigurationManager.getProperty("path.page.foodcort");
    private static final String ADMINCORT_PATH = ConfigurationManager.getProperty("path.page.admincort");

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_LOCALE = "locale";

    LoginCommand(UserReceiver receiver) {
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

        if ((Boolean)content.getRequestAttributes().get("authorization")) {
            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            if(user.getRole() == Role.CLIENT) {
                requestResult = new RequestResult(FOODCORT_PATH, NavigationType.REDIRECT);
            } else {
                requestResult = new RequestResult(ADMINCORT_PATH, NavigationType.REDIRECT);
            }
        } else {
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            if (locale == null || locale.equals("en_US")) {
                locale = "EN";
            } else {
                locale = "RU";
            }
            content.getRequestAttributes().put("errorLoginPassMessage",
                MessageManager.valueOf("EN").getMessage("message.loginerror"));
            requestResult = new RequestResult(ConfigurationManager.getProperty("path.page.login"), NavigationType.FORWARD);
        }
        return requestResult;
    }
}
