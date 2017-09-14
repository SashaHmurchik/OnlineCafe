package by.epam.cafe.dao.impl;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.constant.SignUpAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.UserDao;
import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.entity.Role;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.util.HashPassword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDAO implements UserDao {
    private static final String FIND_USER_BY_EMAIL =
            "select id from user where email=?";
    private static final String FIND_USER_BY_EMAIL_PASSWORD =
            "SELECT * FROM user WHERE email=? AND password=?";
    private static final String FIND_ALL_USERS =
            "SELECT * FROM user";
    private static final String FIND_USER_BY_ID =
            "SELECT * FROM user WHERE id = ?";
    private static final String INSERT_USER =
            "INSERT INTO user (role, name, surname, email, phone, passport, " +
                    "loyalty_points, password, avatar) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String FIND_USER_BY_SURNAME =
            "SELECT * FROM user WHERE surname = ?";
    private static final String FIND_USER_BY_PASSPORT =
            "SELECT * FROM user WHERE passport = ?";
    private static final String UPDATE_USER_BY_ID =
            "UPDATE user " +
                    "SET " +
                    "name = ?," +
                    "surname = ?," +
                    "email = ?," +
                    "phone = ?," +
                    "passport = ?" +
                    "WHERE id = ?";
    private static final String UPDATE_USER_PASSWORD =
            "UPDATE user " +
                    "SET " +
                    "password = ? " +
                    "WHERE email = ?";
    private static final String FIND_PHONE_BY_EMAIL =
            "SELECT phone " +
                    "FROM user " +
                    "WHERE email = ?";
    private static final String UPDATE_LOYALTY_POINT_BY_USER_ID =
            "UPDATE user " +
                    "SET " +
                    "loyalty_points = ? " +
                    "WHERE id = ?";

    @Override
    public boolean findUserByEmail(String eMail) throws DAOException {
        boolean result = false;
        Integer id = null;

        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)){
            statement.setString(1, eMail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when find user by email " + eMail + " ", e);
        }
        if (id != null) {
            result = true;
        }
        return result;
    }

    @Override
    public User findUserByEmailAndPassword(String eMail, String password) throws DAOException {

        User user = null;

        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_PASSWORD)) {
            statement.setString(1, eMail);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when select user by email and password " + eMail + " ", e);
        }

       return user;
    }

    @Override
    public void addUser(RequestContent content) throws DAOException {
        String email = (String)content.getRequestParameters().get(SignUpAtr.EMAIL_PARAM)[0];
        String passport = (String)content.getRequestParameters().get(SignUpAtr.PASSPOET_PARAM)[0];
        String password = (String)content.getRequestParameters().get(SignUpAtr.PASSWORD_PARAM)[0];
        String name = (String)content.getRequestParameters().get(SignUpAtr.NAME_PARAM)[0];
        String surname = (String)content.getRequestParameters().get(SignUpAtr.SURNAME_PARAM)[0];
        String phoneNumber = (String)content.getRequestParameters().get(SignUpAtr.PHONE_PARAM)[0];
        String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);

        try(PreparedStatement statement = connection.prepareStatement(INSERT_USER)){
           statement.setString(1, Role.CLIENT.toString().toLowerCase());
           statement.setString(2, name);
           statement.setString(3, surname);
           statement.setString(4, email.toUpperCase());
           statement.setString(5, phoneNumber);
           statement.setString(6, passport);
           statement.setInt(7, 3);
           statement.setString(8, HashPassword.md5Custom(password));
           statement.setString(9, "image/user/default.jpg");
           statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Fail when add user ", e);
        }
    }

    @Override
    public User findUserById(Integer id) throws DAOException {
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when select user by id " , e);
        }
        return user;
    }

    @Override
    public List<User> findUserBySurname(String surname) throws DAOException {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_SURNAME)) {
            statement.setString(1, surname);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                users.add(ResultSetConverter.createUserEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when select user by surname " , e);
        }
        return users;
    }

    @Override
    public List<User> findAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = ResultSetConverter.createUserEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when select all users " , e);
        }
        return users;
    }

    @Override
    public User findUserByPassport(String passport) throws DAOException {
        User user = null;

        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_PASSPORT)) {
            statement.setString(1, passport);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when select user by passport " , e);
        }
        return user;
    }

    @Override
    public boolean updateUser(RequestContent content) throws DAOException {
        boolean result = false;
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);
        Integer userId = user.getId();

        String email = (String)content.getRequestParameters().get(SignUpAtr.EMAIL_PARAM)[0];
        String passport = (String)content.getRequestParameters().get(SignUpAtr.PASSPOET_PARAM)[0];
        String name = (String)content.getRequestParameters().get(SignUpAtr.NAME_PARAM)[0];
        String surname = (String)content.getRequestParameters().get(SignUpAtr.SURNAME_PARAM)[0];
        String phoneNumber = (String)content.getRequestParameters().get(SignUpAtr.PHONE_PARAM)[0];

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setString(5, passport);
            statement.setInt(6, user.getId());

            if (statement.executeUpdate() != 0) {
                result = true;
                user.setName(name);
                user.setSurname(surname);
                user.setMail(email);
                user.setPhone(phoneNumber);
                user.setPassport(passport);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when update user ", e);
        }
        return result;
    }

    @Override
    public boolean updateUserPassword(String eMail, String password) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(1, password);
            statement.setString(2, eMail);

            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when update user password by email", e);
        }
        return result;
    }

    @Override
    public String getPhoneByEmail(String eMail) throws DAOException {
        String result = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_PHONE_BY_EMAIL)) {
            statement.setString(1, eMail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString(SignUpAtr.PHONE_PARAM);
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when find phone by eNail ", e);
        }
        return result;
    }

    @Override
    public boolean updateLoyaltyPointByUserId(Integer loyaltyPoint, Integer userId) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LOYALTY_POINT_BY_USER_ID)) {
            statement.setInt(1, loyaltyPoint);
            statement.setInt(2, userId);

            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Fail when update loyalty points by user id", e);
        }
        return result;
    }
}
