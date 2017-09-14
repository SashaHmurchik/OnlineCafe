package by.epam.cafe.dao.impl;

import by.epam.cafe.constant.RequestParam;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.OrderDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OrderDaoImpl extends AbstractDAO implements OrderDao {
    private static final String FIND_ORDER_BY_USER =
            "SELECT * FROM epamcafe.`order` WHERE user_id = ?";
    private static final String FIND_ORDER_BY_DELIVERY_DATE =
            "SELECT * FROM epamcafe.`order` " +
                    "WHERE delivery_date >= ? " +
                    "AND delivery_date <= ?";
    private static final String FIND_ORDER_BY_ID =
            "SELECT * FROM epamcafe.`order` " +
                    "WHERE id = ?";
    private static final String INSERT_NEW_ORDER =
            "INSERT INTO epamcafe.`order` (user_id, date, delivery_date, paid, price) " +
                    "VALUES(?,now(),?,?,?)";
    private static final String INSERT_DISH_IN_ORDER =
            "INSERT INTO epamcafe.`order_dish` (order_id, dish_id, number) " +
                    "VALUES(?,?,?)";
    private static final String FIND_DISHES_BY_ORDER =
            "SELECT * FROM epamcafe.`order_dish` WHERE order_id = ?";
    private static final String UPDATE_PAID_STATUS =
            "UPDATE `epamcafe`.`order` " +
                    "SET `paid` = ? " +
                    "WHERE `id` = ?";
    private static final String UPDATE_DELIVERY_STATUS =
            "UPDATE `epamcafe`.`order` " +
                    "SET `delivery_status` = ? " +
                    "WHERE `id` = ?";
    private static final String DELETE_ORDER_BY_ID =
            "DELETE FROM `epamcafe`.`order` " +
                    "WHERE id = ?";
    private static final String DELETE_FROM_ORDER_DISH_BY_ORDER_ID =
            "DELETE FROM `epamcafe`.`order_dish` " +
                    "WHERE order_id = ?";


    @Override
    public List<Order> findAllOrderByUser(User user) throws DAOException {
        List<Order> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_USER)) {
            int id = user.getId();
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order  order = ResultSetConverter.createOrderEntity(resultSet, user);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find order by user", e);
        }
        return list;
    }

    @Override
    public List<Order> findAllOrderByDeliveryDate(LocalDate startDate, LocalDate endDate) throws DAOException {
        List<Order> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_DELIVERY_DATE)) {
            statement.setString(1, startDate.toString());
            statement.setString(2, endDate.toString());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order  order = ResultSetConverter.createOrderEntity(resultSet);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find order by date", e);
        }
        return list;
    }

    @Override
    public Integer addNewOrder(RequestContent content) throws DAOException {
        Integer autoIncrementedOrderId = null;
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);

        Integer userId = user.getId();
        BigDecimal price = user.getBasketPrice();

        String deliveryString = content.getRequestParameters().get(RequestParam.DELIVERY_DATE)[0];
        String[] deliveryArray = deliveryString.split("-");

        LocalDate deliveryDate = LocalDate.of(Integer.parseInt(deliveryArray[0]), Integer.parseInt(deliveryArray[1]), Integer.parseInt(deliveryArray[2]));
        try(PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, userId);
            statement.setDate(2, Date.valueOf(deliveryDate));
            statement.setBoolean(3, false);
            statement.setDouble(4, price.doubleValue());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            autoIncrementedOrderId = rs.getInt(1);

        } catch (SQLException e) {
            throw new DAOException("Fail when add order ", e);
        }

        return autoIncrementedOrderId;
    }

    @Override
    public void addDishInOrder(Integer autoIncrementedOrderId, Dish dish, Integer number) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DISH_IN_ORDER)) {
            statement.setInt(1, autoIncrementedOrderId);
            statement.setInt(2, dish.getId());
            statement.setInt(3, number);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Fail when add dish in order ", e);
        }
    }

    @Override
    public HashMap<Dish, Integer> findAllDishesByOrderId(Integer orderId) throws DAOException {
        HashMap<Dish, Integer> map = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_DISHES_BY_ORDER)) {
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               int dishId = resultSet.getInt("dish_id");
               TransactionHelper helper = new TransactionHelper();
               DishDaoImpl dishDao = new DishDaoImpl();
               helper.beginTransaction(dishDao);
               Dish dish = dishDao.findDishById(dishId);
               helper.endTransaction();
               int amount = resultSet.getInt("number");
               map.put(dish, amount);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when find dishes by order ", e);
        }
        return map;
    }

    @Override
    public boolean updateOrderPaidStatus(Order order, boolean status) throws DAOException {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PAID_STATUS)) {
            statement.setBoolean(1, status);
            statement.setInt(2, order.getId());

            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when update order paid status ", e);
        }

        return result;
    }

    @Override
    public boolean updateOrderDeliveryStatus(Order order, boolean deliveryStatus) throws DAOException {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DELIVERY_STATUS)) {
            statement.setBoolean(1, deliveryStatus);
            statement.setInt(2, order.getId());

            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when update order delivery status ", e);
        }
        return result;
    }

    @Override
    public boolean deleteOrderById(Integer orderId) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_BY_ID)) {
            statement.setInt(1, orderId);
            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when delete order by id ", e);
        }
        return result;
    }

    @Override
    public boolean deleteFromOrderDishByOrderId(Integer orderId) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_ORDER_DISH_BY_ORDER_ID)) {
            statement.setInt(1, orderId);
            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when delete from order_dish by id ", e);
        }
        return result;
    }
}
