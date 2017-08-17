package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.ReceiverException;
import org.apache.commons.fileupload.RequestContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public interface OrderReceiver extends Receiver {
    boolean makeNewOrder(RequestContent content) throws ReceiverException;
    HashMap<Order, HashMap<Dish, Integer>> findAllOrderByUser(User user) throws ReceiverException;
    HashMap<Order, HashMap<Dish, Integer>> findAllOrderByDeliveryDate(LocalDate startDate, LocalDate endDate) throws ReceiverException;
    boolean updatePaidStatus(Order order, boolean status) throws ReceiverException;
    boolean updateDeliveryStatus(Order order, boolean deliveryStatus) throws ReceiverException;
    Map<Order, HashMap<Dish, Integer>> sorteOrders(RequestContent content) throws ReceiverException;
}
