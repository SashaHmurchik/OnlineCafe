package by.epam.cafe.dao;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.Category;
import by.epam.cafe.exception.DAOException;

import java.util.List;

public interface DishDao {

    List<Dish> findDishByCategory(Category category, Boolean archiveStatus) throws DAOException;
    List<Dish> findDishByKitchen(Kitchen kitchen, Boolean archiveStatus) throws DAOException;
    List<Dish> findAllDishByArchiveStatus(Boolean archiveStatus) throws DAOException;
    List<Dish> findDishByCategoryAndKitchen(Category category, Kitchen kitchen, Boolean archiveStatus) throws DAOException;
    Dish findDishById(Integer kitchenId) throws DAOException;
    boolean addNewDish(RequestContent content) throws DAOException;
    boolean updateArchiveStatusByDishId(Boolean arhiveStatus, Integer dishId) throws DAOException;
    boolean updateArchiveStatusByKitchenID(Boolean archiveStatus, Integer kitchenId) throws DAOException;
    boolean updateArchiveStatusByCategoryId(Boolean archiveStaus, Integer categoryId) throws DAOException;
}
