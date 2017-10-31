package by.epam.cafe.dao;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface OrderDao {
    List<Order> findAllOrderByUser(User user) throws DAOException;
    List<Order> findAllOrderByDeliveryDate(LocalDate startDate, LocalDate endDate) throws DAOException;
    Integer addNewOrder(RequestContent content) throws DAOException;
    void addDishInOrder(Integer orderId, Dish dish, Integer number) throws DAOException;
    HashMap<Dish,Integer> findAllDishesByOrderId(Integer orderId) throws DAOException;
    boolean updateOrderPaidStatus(Order order, boolean status) throws DAOException;
    boolean updateOrderDeliveryStatus(Order order, boolean deliveryStatus) throws DAOException;
    boolean deleteOrderById(Integer orderId) throws DAOException;
    boolean deleteFromOrderDishByOrderId(Integer orderId) throws DAOException;
}
