package by.epam.cafe.dao.impl;

import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.CategoryDao;
import by.epam.cafe.entity.Category;
import by.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends AbstractDAO implements CategoryDao {
    private static final String FIND_CATEGORY_BY_ID =
            "SELECT * FROM category WHERE id=?";
    private static final String FIND_ALL_CATEGORY =
            "SELECT * FROM category";
    private static final String FIND_ALL_CATEGORY_BY_ARCHIVE_STATUS =
            "SELECT * FROM category " +
                    "WHERE archive = ?";
    private static final String UPDATE_ARHIVE_STATUS =
            "UPDATE category " +
                    "SET " +
                    "archive = ? " +
                    "WHERE id = ?";
    private static final String INSERT_NEW_CATEGORY =
            "INSERT INTO category " +
                    "(name) VALUES(?)";
    private static final String FIND_CATEGORY_BY_NAME =
            "SELECT * FROM category " +
                    "WHERE name = ?";

    @Override
    public Category findCategoryById(Integer id) throws DAOException {
        Category category = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_CATEGORY_BY_ID)) {
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category = ResultSetConverter.createCategoryEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when find Kitchen by Id", e);
        }
        return category;
    }

    @Override
    public List<Category> findAll() throws DAOException {
        List<Category> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_CATEGORY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = ResultSetConverter.createCategoryEntity(resultSet);
                list.add(category);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find all category", e);
        }
        return list;
    }

    @Override
    public List<Category> findAllByArchiveStatus(Boolean archiveStatus) throws DAOException {
        List<Category> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_CATEGORY_BY_ARCHIVE_STATUS)) {
            statement.setBoolean(1, archiveStatus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = ResultSetConverter.createCategoryEntity(resultSet);
                list.add(category);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find all category by archeve status.", e);
        }
        return list;
    }

    @Override
    public boolean updateArchiveStatus(Boolean archiveStatus, Integer categoryId) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ARHIVE_STATUS)) {
            statement.setBoolean(1, archiveStatus);
            statement.setInt(2, categoryId);
            if (statement.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during update category archive status", e);
        }
        return result;
    }

    @Override
    public boolean addNewCategory(String categoryName) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CATEGORY)) {
            statement.setString(1, categoryName);
            return statement.executeUpdate() == 1;
        } catch(SQLException e) {
            throw new DAOException("Exception during add new category", e);
        }
    }

    @Override
    public Category findCategoryByName(String categoryName) throws DAOException {
        Category category = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_CATEGORY_BY_NAME)) {
            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                category = ResultSetConverter.createCategoryEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find category by name", e);
        }
        return category;
    }
}
