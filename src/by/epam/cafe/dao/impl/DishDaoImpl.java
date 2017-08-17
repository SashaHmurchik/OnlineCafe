package by.epam.cafe.dao.impl;

import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.DishDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.charackter.Category;
import by.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDaoImpl extends AbstractDAO implements DishDao {
    private static final String FIND_ALL_DISH =
            "SELECT * FROM dish";
    private static final String FIND_DISH_BY_CATEGORY =
            "SELECT * FROM dish WHERE category_id=?";
    private static final String FIND_DISH_BY_KITCHEN =
            "SELECT * FROM dish WHERE kitchen_id=?";
    private static final String FIND_DISH_BY_CATEGORY_AND_KITCHEN =
            "SELECT * FROM dish WHERE category_id=? AND kitchen_id=?";
    private static final String FIND_DISH_BY_ID =
            "SELECT * FROM dish WHERE id=?";

    @Override
    public List<Dish> findAll() throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_DISH)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish  dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find all dish", e);
        }

        return list;
    }

    @Override
    public List<Dish> findDishByCategory(Category category) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_CATEGORY)) {
            int id = category.getId();
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish  dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find dish by category", e);
        }
        return list;
    }

    @Override
    public List<Dish> findDishByKitchen(Kitchen kitchen) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_KITCHEN)) {
            int id = kitchen.getId();
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish  dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find dish by kitchen", e);
        }
        return list;
    }

    @Override
    public List<Dish> findDishByCategoryAndKitchen(Category category, Kitchen kitchen) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_CATEGORY_AND_KITCHEN)) {
            int kitchenId = kitchen.getId();
            int categoryId = category.getId();
            statement.setInt(1, categoryId);
            statement.setInt(2, kitchenId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish  dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find dish by category and kitchen", e);
        }
        return list;
    }

    @Override
    public Dish findDishById(Integer id) throws DAOException {
        Dish dish = null;
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = ResultSetConverter.createDishEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find dish by id", e);
        }
        return dish;
    }

    public static void main(String[] args) {
        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dao = new DishDaoImpl();
        helper.beginTransaction(dao);
        try {
            Category category = new Category();
            category.setId(18);
            List<Dish> list = dao.findDishByCategory(category);
            list.stream().forEach(o -> System.out.println(o));

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
