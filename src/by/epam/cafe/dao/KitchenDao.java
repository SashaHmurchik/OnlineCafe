package by.epam.cafe.dao;

import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.exception.DAOException;

import java.util.List;

public interface KitchenDao {
    Kitchen findKitchenById(Integer id) throws DAOException;
    List<Kitchen> findAll() throws DAOException;
}
