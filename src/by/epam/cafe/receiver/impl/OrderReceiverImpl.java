package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.impl.OrderDaoImpl;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.entity.charackter.Role;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.OrderReceiver;
import by.epam.cafe.util.OrderMapSorter;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderReceiverImpl implements OrderReceiver {
    private static final String SORT_PARAM = "sort";
    private static final String ORDER_MAP_PARAM = "orderMap";

    private HashMap<Order, HashMap<Dish, Integer>> findDishesByOrder(List<Order> orderList, OrderDaoImpl orderDao) throws ReceiverException {
        HashMap<Order, HashMap<Dish, Integer>> orderDishMap = new HashMap<>();
        try {
            for (Order order : orderList) {
                HashMap<Dish, Integer> dishMap = orderDao.findAllDishesByOrder(order);
                orderDishMap.put(order, dishMap);
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem when find all dishes by order logic :", e);
        }

        return orderDishMap;
    }

    @Override
    public boolean makeNewOrder(RequestContent content) throws ReceiverException {
        boolean result = false;
        TransactionHelper helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        try {
            Integer autoIncrementedOrderId = orderDao.addNewOrder(content);

            User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
            HashMap<Dish, Integer> basket = user.getBasket();
            for (Dish dish : basket.keySet()) {
                orderDao.addDishInOrder(autoIncrementedOrderId, dish, basket.get(dish));
            }
            user.setBasket(null);
            user.setBasketPrice(null);
            user.setAvailableForDeliveryDays(null);
            result = true;
        } catch (DAOException e) {
            throw new ReceiverException("Problem when make new order logic :", e);
        }
        helper.endTransaction();

        return result;
    }

    @Override
    public HashMap<Order, HashMap<Dish, Integer>> findAllOrderByUser(User user) throws ReceiverException {

        TransactionHelper helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        List<Order> orderList = null;
        try {
            orderList = orderDao.findAllOrderByUser(user);
        } catch (DAOException e) {
            throw new ReceiverException("Problem when find all orders by user logic :", e);
        }
        HashMap<Order, HashMap<Dish, Integer>> orderDishMap = findDishesByOrder(orderList, orderDao);
        helper.endTransaction();

        return orderDishMap;
    }

    @Override
    public HashMap<Order, HashMap<Dish, Integer>> findAllOrderByDeliveryDate(LocalDate startDate, LocalDate endDate) throws ReceiverException {

        TransactionHelper helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        List<Order> orderList = null;
        try {
            orderList = orderDao.findAllOrderByDeliveryDate(startDate, endDate);
        } catch (DAOException e) {
            throw new ReceiverException("Problem when find all orders bu delivery date logic :", e);
        }
        HashMap<Order, HashMap<Dish, Integer>> orderDishMap = findDishesByOrder(orderList, orderDao);
        helper.endTransaction();

        return orderDishMap;
    }

    @Override
    public boolean updatePaidStatus(Order order, boolean status) throws ReceiverException {
        boolean result = false;
        TransactionHelper  helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        try {
            result = orderDao.updateOrderPaidStatus(order, status);
            order.setPaid(status);
        } catch (DAOException e) {
            throw new ReceiverException("Problem when update order paid status logic :", e);
        }
        helper.endTransaction();
        return result;
    }

    @Override
    public boolean updateDeliveryStatus(Order order, boolean deliveryStatus) throws ReceiverException {
        boolean result = false;

        TransactionHelper helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        try {
            result = orderDao.updateOrderDeliveryStatus(order, deliveryStatus);
            order.setDeliveryStatus(deliveryStatus);
        } catch (DAOException e) {
            throw new ReceiverException("Problem when update order delivery status logic :", e);
        }
        helper.endTransaction();

        return result;
    }

    @Override
    public Map<Order, HashMap<Dish, Integer>> sorteOrders(RequestContent content) throws ReceiverException {

        Map<Order, HashMap<Dish, Integer>> orderMap = (Map<Order, HashMap<Dish, Integer>>)content.getSessionAttributes().get(ORDER_MAP_PARAM);
        String comp = content.getRequestParameters().get(SORT_PARAM)[0];
        Comparator<Order> comparator = null;
        switch (comp.toUpperCase()) {
            case "DEL_DATE" :
                comparator = OrderMapSorter.comparatorByDeliveryDate;
                break;
            case "PAID" :
                comparator = OrderMapSorter.comparatorByPaid;
                break;
            default:
                comparator = OrderMapSorter.comparatorByDeliveryDate;
        }
        OrderMapSorter sorter = new OrderMapSorter();
        return sorter.sortOrders(orderMap, comparator);
    }
/*
    public static void main(String[] args) {
        OrderReceiverImpl orderReceiver = new OrderReceiverImpl();
        User user = new User();
        user.setId(1);
        user.setRole(Role.CLIENT);
        user.setName("njfjf");
        user.setSurname("kfkfk");
        user.setAvatar("kkdkd");
        user.setMail("kfkfk");
        user.setLoyaltyPoint(1);
        user.setPassword("kfkkf");
        user.setPassport("kfkf");
        user.setPhone("kkfkfk");
        try {
            System.out.println(orderReceiver.findAllOrderByUser(user).size());
        } catch(ReceiverException e) {
            e.printStackTrace();
        }
    }
    */
}
