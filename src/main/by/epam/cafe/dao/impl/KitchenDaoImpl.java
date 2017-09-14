package by.epam.cafe.dao.impl;

import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.KitchenDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.entity.Category;
import by.epam.cafe.entity.Kitchen;
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

    private static final String FIND_ALL_KITCHEN_BY_ARCHIVE_STATUS =
            "SELECT * FROM epamcafe.kitchen " +
                    "WHERE archive = ?";

    private static final String INSERT_KITCHEN =
            "INSERT INTO epamcafe.kitchen " +
                    "(name, phone, address, site, email, picture) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";

    private static final String FIND_KITCHEN_ID_BY_NAME =
            "SELECT id FROM epamcafe.kitchen " +
                    "WHERE name = ?";
    private static final String UPDATE_KITCHEN_ARCHIVE_STATUS =
            "UPDATE epamcafe.kitchen " +
                    "SET " +
                    "archive = ? " +
                    "WHERE id = ?";
    private static final String UPDATE_KITCHEN =
            "UPDATE epamcafe.kitchen " +
                    "SET " +
                    "address = ?, " +
                    "site = ?, " +
                    "email = ?, " +
                    "phone = ?, " +
                    "archive = ? " +
                    "WHERE id = ?";


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
    public List<Kitchen> findAllByArchiveStatus(Boolean archiveStatus) throws DAOException {
        List<Kitchen> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_KITCHEN_BY_ARCHIVE_STATUS)) {
            statement.setBoolean(1, archiveStatus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Kitchen kitchen = ResultSetConverter.createKitchenEntity(resultSet);
                list.add(kitchen);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during find all kitchen by archeve status.", e);
        }
        return list;
    }

    @Override
    public boolean addKitchen(RequestContent content) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_KITCHEN)) {
            String kitchenName = content.getRequestParameters().get(EntityAtr.KITCHEN_NAME_PARAM)[0];
            String kitchenEmail = content.getRequestParameters().get(EntityAtr.KITCHEN_EMAIL_PARAM)[0];
            String kitchenSite = content.getRequestParameters().get(EntityAtr.KITCHEN_SITE_PARAM)[0];
            String kitchenAddress = content.getRequestParameters().get(EntityAtr.KITHEN_ADDRESS_PARAM)[0];
            String kitchenPhone = content.getRequestParameters().get(EntityAtr.KITCHEN_PHONE_PARAM)[0];
            String picture = (String)content.getRequestAttributes().get(EntityAtr.PICTURE_PARAM);

            statement.setString(1, kitchenName);
            statement.setString(2, kitchenPhone);
            statement.setString(3, kitchenAddress);
            statement.setString(4, kitchenSite);
            statement.setString(5, kitchenEmail);
            statement.setString(6, picture);
            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch(SQLException e) {
            throw new DAOException("Exception during insert kitchen", e);
        }
        return result;
    }

    @Override
    public boolean updateArchiveStatus(Integer kitchenId, Boolean archiveStatus) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_KITCHEN_ARCHIVE_STATUS)) {
            statement.setBoolean(1, archiveStatus);
            statement.setInt(2, kitchenId);
            if (statement.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during update kitchen whith id " + kitchenId + " archive status ", e);
        }
        return result;
    }

    @Override
    public boolean updateKitchen(Kitchen kitchen) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_KITCHEN)) {

            statement.setString(1, kitchen.getAddress());
            statement.setString(2, kitchen.getSite());
            statement.setString(3, kitchen.getEmail());
            statement.setString(4, kitchen.getPhone());
            statement.setBoolean(5, kitchen.getArchiveStatus());
            statement.setInt(6, kitchen.getId());
            if (statement.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during update kitchen whith id " + kitchen.getId() + ". ", e);
        }
        return result;
    }

}
