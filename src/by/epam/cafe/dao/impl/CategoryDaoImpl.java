package by.epam.cafe.dao.impl;

import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.CategoryDao;
import by.epam.cafe.entity.charackter.Category;
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
}
