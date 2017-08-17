package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.exception.ReceiverException;

public interface DishReceiver extends Receiver {
    void showDish(RequestContent content) throws ReceiverException;
}
