package by.epam.cafe.dao.impl;

import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.KitchenDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.charackter.Category;
import by.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenDaoImpl extends AbstractDAO implements KitchenDao {
    private static final String FIND_KITCHEN_BY_ID =
            "SELECT * FROM kitchen WHERE id=?";

    private static final String FIND_ALL_KITCHENS =
            "SELECT * FROM epamcafe.kitchen";

    @Override
    public Kitchen findKitchenById(Integer id) throws DAOException {
        Kitchen kitchen = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_KITCHEN_BY_ID)) {
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                kitchen = ResultSetConverter.createKitchenEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when find Kitchen by Id", e);
        }
        return kitchen;
    }

    @Override
    public List<Kitchen> findAll() throws DAOException {
        List<Kitchen> list  = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_KITCHENS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Kitchen kitchen = ResultSetConverter.createKitchenEntity(resultSet);
                list.add(kitchen);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find all kitchens", e);
        }
        return list;
    }

    public static void main(String[] args) {
        TransactionHelper helper = new TransactionHelper();
        KitchenDaoImpl dao = new KitchenDaoImpl();
        helper.beginTransaction(dao);
        try {
            dao.findAll().stream().forEach(System.out::println);
        } catch(DAOException e) {
            e.printStackTrace();
        }
        helper.endTransaction();
    }
}
