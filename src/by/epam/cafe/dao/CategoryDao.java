package by.epam.cafe.dao;

import by.epam.cafe.entity.charackter.Category;
import by.epam.cafe.exception.DAOException;

import java.util.List;

public interface CategoryDao {
    Category findCategoryById(Integer id) throws DAOException;
    List<Category> findAll() throws DAOException;
}
