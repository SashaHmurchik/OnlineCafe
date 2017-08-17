package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.ReceiverException;

import java.util.ArrayList;
import java.util.List;

public interface UserReceiver extends Receiver {
    void loginLogic(RequestContent content) throws ReceiverException;
    boolean isEmailExist(RequestContent content) throws ReceiverException;
    void signUpLogic(RequestContent content) throws ReceiverException;
    boolean  addToBasket(RequestContent content) throws ReceiverException;
    boolean removeFromBasket(RequestContent content) throws ReceiverException;
    List<User> findUserBySurname(RequestContent content) throws ReceiverException;
}
