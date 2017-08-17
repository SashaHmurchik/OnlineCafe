package by.epam.cafe.dao;

import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.charackter.Category;
import by.epam.cafe.exception.DAOException;

import java.util.List;

public interface DishDao {
    List<Dish> findAll() throws DAOException;
    List<Dish> findDishByCategory(Category category) throws DAOException;
    List<Dish> findDishByKitchen(Kitchen kitchen) throws DAOException;
    List<Dish> findDishByCategoryAndKitchen(Category category, Kitchen kitchen) throws DAOException;
    Dish findDishById(Integer id) throws DAOException;
}
