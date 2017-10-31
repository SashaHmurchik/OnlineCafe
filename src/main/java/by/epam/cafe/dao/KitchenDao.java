package by.epam.cafe.dao;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.exception.DAOException;

import java.util.List;

public interface KitchenDao {
    Kitchen findKitchenById(Integer id) throws DAOException;
    List<Kitchen> findAllByArchiveStatus(Boolean archiveStatus) throws DAOException;
    boolean addKitchen(RequestContent content)throws DAOException;
    boolean updateArchiveStatus(Integer kitchenId, Boolean archiveStatus) throws DAOException;
    boolean updateKitchen(Kitchen kitchen) throws DAOException;
}
