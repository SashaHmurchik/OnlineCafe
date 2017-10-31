package by.epam.cafe.dao.impl;

import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.DishDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.Category;
import by.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDaoImpl extends AbstractDAO implements DishDao {
    private static final String FIND_ALL_DISH =
            "SELECT * FROM dish";
    private static final String FIND_ALL_DISH_BY_ARCHIVE_STATUS =
            "SELECT * FROM dish WHERE archive = ?";
    private static final String FIND_DISH_BY_CATEGORY =
            "SELECT * FROM dish WHERE category_id=? AND archive = ?";
    private static final String FIND_DISH_BY_KITCHEN =
            "SELECT * FROM dish WHERE kitchen_id=? AND archive = ?";
    private static final String FIND_DISH_BY_CATEGORY_AND_KITCHEN_AND_ARCHIVE_STATUS =
            "SELECT * FROM dish WHERE category_id=? AND kitchen_id=? AND archive = ?";
    private static final String FIND_DISH_BY_ID =
            "SELECT * FROM dish WHERE id=?";
    private static final String INSERT_DISH =
            "INSERT INTO dish " +
                    "(name, " +
                    "kitchen_id, " +
                    "category_id, " +
                    "price, " +
                    "description, " +
                    "amount, " +
                    "picture) " +
                    "VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_DISH_ARCHIVE_STATUS_BY_DISH_ID =
            "UPDATE dish " +
                    "SET " +
                    "archive = ? " +
                    "WHERE id = ?";
    private static final String UPDATE_DISH_ARCHIVE_STATUS_BY_KITCHEN_ID =
            "UPDATE dish " +
                    "SET " +
                    "archive = ? " +
                    "WHERE kitchen_id = ?";
    private static final String UPDATE_DISH_ARCHIVE_STATUS_BY_CATEGORY_ID =
            "UPDATE dish " +
                    "SET " +
                    "archive = ? " +
                    "WHERE category_id = ?";


    @Override
    public List<Dish> findDishByCategory(Category category, Boolean archiveStatus) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_CATEGORY)) {
            int id = category.getId();
            statement.setBoolean(2, archiveStatus);
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
    public List<Dish> findDishByKitchen(Kitchen kitchen, Boolean archiveStatus) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_KITCHEN)) {
            int id = kitchen.getId();
            statement.setInt(1, id);
            statement.setBoolean(2, archiveStatus);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(ResultSetConverter.createDishEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find dish by kitchen", e);
        }
        return list;
    }

    @Override
    public List<Dish> findAllDishByArchiveStatus(Boolean archiveStatus) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_DISH_BY_ARCHIVE_STATUS)) {
            statement.setBoolean(1, archiveStatus);
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
    public List<Dish> findDishByCategoryAndKitchen(Category category, Kitchen kitchen, Boolean archiveStatus) throws DAOException {
        List<Dish> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_CATEGORY_AND_KITCHEN_AND_ARCHIVE_STATUS)) {
            int kitchenId = kitchen.getId();
            int categoryId = category.getId();
            statement.setInt(1, categoryId);
            statement.setInt(2, kitchenId);
            statement.setBoolean(3, archiveStatus);

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

    @Override
    public boolean addNewDish(RequestContent content) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DISH)) {
            String dishName = content.getRequestParameters().get(EntityAtr.DISH_NAME_PARAM)[0];
            String categoryId = content.getRequestParameters().get(EntityAtr.DISH_CATEGORY_ID_PARAM)[0];
            String price = content.getRequestParameters().get(EntityAtr.DISH_PRICE_PARAM)[0];
            String dishDescription = content.getRequestParameters().get(EntityAtr.DISH_DESCRIPTION_PARAM)[0];
            String dishAmount = content.getRequestParameters().get(EntityAtr.DISH_AMOUNT_PARAM)[0];
            String dishPicture = (String)content.getRequestAttributes().get(EntityAtr.PICTURE_PARAM);
            String kitchenId = content.getRequestParameters().get(EntityAtr.KITCHEN_ID_PARAM)[0];

            statement.setString(1, dishName);
            statement.setString(2, kitchenId);
            statement.setString(3, categoryId);
            statement.setString(4, price);
            statement.setString(5, dishDescription);
            statement.setString(6, dishAmount);
            statement.setString(7, dishPicture);

            if (statement.executeUpdate() == 1) {
                result = true;
            }
        } catch(SQLException e) {
            throw new DAOException("Exception during insert kitchen", e);
        }
        return result;
    }

    @Override
    public boolean updateArchiveStatusByDishId(Boolean archiveStatus, Integer dishId) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DISH_ARCHIVE_STATUS_BY_DISH_ID)) {
            statement.setBoolean(1, archiveStatus);
            statement.setInt(2, dishId);
            if (statement.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during update dish archive status ", e);
        }
        return result;
    }

    @Override
    public boolean updateArchiveStatusByKitchenID(Boolean archiveStatus, Integer kitchenId) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DISH_ARCHIVE_STATUS_BY_KITCHEN_ID)) {
            statement.setBoolean(1, archiveStatus);
            statement.setInt(2, kitchenId);
            statement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new DAOException("Exception during update dish archive status by kitchen id ", e);
        }
        return result;
    }

    @Override
    public boolean updateArchiveStatusByCategoryId(Boolean archiveStatus, Integer categoryId) throws DAOException {
        boolean result;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DISH_ARCHIVE_STATUS_BY_CATEGORY_ID)) {
            statement.setInt(2, categoryId);
            statement.setBoolean(1, archiveStatus);
            statement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new DAOException("Exception during update dish archive status by category id ", e);
        }
        return result;
    }
}
