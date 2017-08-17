package by.epam.cafe.command;

import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.receiver.impl.DishReceiverImpl;
import by.epam.cafe.receiver.impl.OrderReceiverImpl;
import by.epam.cafe.receiver.impl.UserReceiverImpl;

import java.util.*;
import  static by.epam.cafe.command.CommandType.*;

public class CommandMap {
    private EnumMap<CommandType, AbstractCommand> map = new EnumMap<>(CommandType.class);
    private static CommandMap instance = new CommandMap();
    private CommandMap() {
        map.put(LOGIN, new LoginCommand(new UserReceiverImpl()));
        map.put(LOGOUT, new LogoutCommand(new UserReceiverImpl()));
        map.put(SIGNUP, new SignUpCommand(new UserReceiverImpl()));
        map.put(SHOWDISH, new ShowDishCommand(new DishReceiverImpl()));
        map.put(LOCALE, new LocaleCommand(new UserReceiverImpl()));
        map.put(ADDTOBASKET, new AddToBasketCommand(new UserReceiverImpl()));
        map.put(REMOVEFROMBASKET, new RemoveFromBasketCommand(new UserReceiverImpl()));
        map.put(MAKEORDER, new MakeOrderCommand(new OrderReceiverImpl()));
        map.put(SHOWMYORDER, new ShowMyOrderCommand(new OrderReceiverImpl()));
        map.put(FINDUSERBYSURNAME, new FindUserBySurnameCommand(new UserReceiverImpl()));
        map.put(SHOWORDERS, new ShowOrdersCommand(new OrderReceiverImpl()));
        map.put(PAYADMIN, new PayForOrderByAdminCommand(new OrderReceiverImpl()));
        map.put(DELIVER, new DeliverOrderCommand(new OrderReceiverImpl()));
        map.put(SHOWORDERBYDATE, new ShowOrderByDateCommand(new OrderReceiverImpl()));
        map.put(SORTORDERMAP, new SortOrderMapCommand(new OrderReceiverImpl()));
    }

    public static CommandMap getInstance() {
        return instance;
    }

    EnumMap<CommandType, AbstractCommand> get(){
        return map;
    }

    public static AbstractCommand defineCommandType(String comamnd) {
        List<AbstractCommand> list = new ArrayList<>();
        CommandType commandType = CommandType.valueOf(comamnd);
        getInstance().map.entrySet().stream().filter(o -> o.getKey().equals(commandType)).forEach(o -> list.add(o.getValue()));
        return list.get(0);
    }

}
