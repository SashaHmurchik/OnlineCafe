package by.epam.cafe.receiver.impl;


import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.impl.OrderDaoImpl;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.OrderReceiver;
import by.epam.cafe.util.OrderMapSorter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderReceiverImpl implements OrderReceiver {
    private static final String SORT_PARAM = "sort";
    private static final String ORDER_MAP_PARAM = "orderMap";
    private static final String ORDER_ID_PARAM = "order_id";

    private HashMap<Order, HashMap<Dish, Integer>> findDishesByOrder(List<Order> orderList, OrderDaoImpl orderDao) throws ReceiverException {
        HashMap<Order, HashMap<Dish, Integer>> orderDishMap = new HashMap<>();
        try {
            for (Order order : orderList) {
                HashMap<Dish, Integer> dishMap = orderDao.findAllDishesByOrderId(order.getId());
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
            user.setBasket();
            user.setBasketCapacity();
            user.setBasketPrice(new BigDecimal(0));
            result = true;
        } catch (DAOException e) {
            throw new ReceiverException("Problem when make new order logic :", e);
        }
        helper.endTransaction();

        return result;
    }

    @Override
    public Map<Order, HashMap<Dish, Integer>> findAllOrderByUser(User user) throws ReceiverException {

        TransactionHelper helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        List<Order> orderList = null;
        try {
            orderList = orderDao.findAllOrderByUser(user);
        } catch (DAOException e) {
            throw new ReceiverException("Problem when find all orders by user logic :", e);
        }
        Map<Order, HashMap<Dish, Integer>> orderMap = findDishesByOrder(orderList, orderDao);
        helper.endTransaction();
        if (!orderMap.isEmpty()) {
            OrderMapSorter orderMapSorter = new OrderMapSorter();
            orderMap = orderMapSorter.sortOrders(orderMap, OrderMapSorter.comparatorByDeliveryDate);
        }
        return orderMap;
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
    public Map<Order, HashMap<Dish, Integer>> sorteOrders(RequestContent content) {

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

        if (orderMap != null) {
            orderMap = sorter.sortOrders(orderMap, comparator);
        }
        return orderMap;
    }

    @Override
    public boolean refuseOrder(RequestContent content) throws ReceiverException {
        boolean result = false;
        Integer orderId = Integer.valueOf(content.getRequestParameters().get(ORDER_ID_PARAM)[0]);

        TransactionHelper helper = new TransactionHelper();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        helper.beginTransaction(orderDao);
        HashMap<Dish, Integer> dishInOrder = null;
        try {
            dishInOrder = orderDao.findAllDishesByOrderId(orderId);
            if (orderDao.deleteFromOrderDishByOrderId(orderId) && orderDao.deleteOrderById(orderId)) {
                result = true;
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem when refuse order logic ",e);
        }
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
        user.setBasket();
        user.setBasketCapacity();
        user.setBasketPrice(new BigDecimal(0));
        for (Dish d : dishInOrder.keySet()) {
            for (int i=0; i < dishInOrder.get(d); i++) {
                user.addToBasket(d);
            }
        }
        helper.endTransaction();
        return result;
    }

}
