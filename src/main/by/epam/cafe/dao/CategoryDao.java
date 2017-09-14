package by.epam.cafe.dao;

import by.epam.cafe.entity.Category;
import by.epam.cafe.exception.DAOException;

import java.util.List;

public interface CategoryDao {
    Category findCategoryById(Integer id) throws DAOException;
    List<Category> findAll() throws DAOException;
    List<Category> findAllByArchiveStatus(Boolean archiveStatus) throws DAOException;
    boolean updateArchiveStatus(Boolean archiveStatus, Integer categoryId) throws DAOException;
    boolean addNewCategory(String categoryName) throws DAOException;
    Category findCategoryByName(String categoryName) throws DAOException;
}
