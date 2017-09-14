package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.ReceiverException;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public interface OrderReceiver extends Receiver {

    /**
     * Logic for add new order in data base.
     * @param content - object with parameters for order:
     *                date, delivery date, price, map of dishes.
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke write operation
     */
    boolean makeNewOrder(RequestContent content) throws ReceiverException;

    /**
     * Find and return all user orders.
     * @param user how made all this orders.
     * @return Map with information about all orders :
     *  keys - Objects of orders, value - HashMap :
     *  keys - Dishes from order, value - amount of dishes.
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    Map<Order, HashMap<Dish, Integer>> findAllOrderByUser(User user) throws ReceiverException;

    /**
     * Find and return all orders for chosen dates.
     * @param startDate
     * @param endDate
     * @return Map with information about all orders :
     *  keys - Objects of orders, value - HashMap :
     *  keys - Dishes from order, value - amount of dishes.
     *  @throws ReceiverException if fail occurs while invoke read operation
     */
    HashMap<Order, HashMap<Dish, Integer>> findAllOrderByDeliveryDate(LocalDate startDate, LocalDate endDate) throws ReceiverException;

    /**
     * Logic for Update paid status of order in data base.
     * @param order order - for update
     * @param status - update status
     *               <code>true</code> - paid
     *               <code>false</code> -not paid
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke update operation
     */
    boolean updatePaidStatus(Order order, boolean status) throws ReceiverException;

    /**
     * Logic for Update delivery status of order in data base.
     * @param order order - for update
     * @param status - update status
     *               <code>true</code> - delivered
     *               <code>false</code> -not delivered
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke update operation
     */
    boolean updateDeliveryStatus(Order order, boolean deliveryStatus) throws ReceiverException;

    /**
     * Logic for Sort all order. Can sort by delivery date and paid status.
     * @param content with map of orders
     * @return sorted order map
     */
    Map<Order, HashMap<Dish, Integer>> sorteOrders(RequestContent content);

    /**
     * Logic for Removes order from data base and put all dishes from order
     * in user basket.
     * @param content with parameters of order id and user id
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke remove operation
     */
    boolean refuseOrder(RequestContent content) throws ReceiverException;
}
