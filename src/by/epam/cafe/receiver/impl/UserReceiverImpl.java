package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.UserDao;
import by.epam.cafe.dao.impl.DishDaoImpl;
import by.epam.cafe.dao.impl.UserDaoImpl;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.pool.ProxyConnection;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.util.HashPassword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserReceiverImpl implements UserReceiver {
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_DISH = "dish";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_ALL_USERS = "allusers";

    @Override
    public void loginLogic(RequestContent content) throws ReceiverException {

        String enterEmail = content.getRequestParameters().get(PARAM_EMAIL)[0];
        String enterPass = HashPassword.md5Custom(content.getRequestParameters().get(PARAM_PASSWORD)[0]);

        TransactionHelper helper = new TransactionHelper();
        AbstractDAO userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        UserDaoImpl userDaoImpl = (UserDaoImpl)userDao;

        User user = null;
        content.getRequestAttributes().put("authorization", new Boolean(false));
        try {
            if (userDaoImpl.findUserByEmail(enterEmail.toUpperCase())) {
                user = userDaoImpl.findUserByEmailAndPassword(enterEmail.toUpperCase(), enterPass);

                if (user != null) {
                    content.getRequestAttributes().put("authorization", new Boolean(true));
                    content.setSessionAttributes("user", user);
                }
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem when login Logic :", e);
        }

        helper.endTransaction();
    }

    @Override
    public boolean isEmailExist(RequestContent content) throws ReceiverException {
        boolean resp = false;
        String enterEmail = content.getRequestParameters().get(PARAM_EMAIL)[0];

        TransactionHelper helper = new TransactionHelper();
        AbstractDAO userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        UserDaoImpl userDaoImpl = (UserDaoImpl)userDao;

        try {
            resp = userDaoImpl.findUserByEmail(enterEmail.toUpperCase());
        } catch (DAOException e) {
            throw new ReceiverException("Problem when check email existance Logic :", e);
        }
        helper.endTransaction();
        return resp;
    }

    @Override
    public void signUpLogic(RequestContent content) throws ReceiverException {

        TransactionHelper helper = new TransactionHelper();
        AbstractDAO userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        UserDaoImpl userDaoImpl = (UserDaoImpl)userDao;

        try {
            userDaoImpl.addUser(content);
            content.getRequestAttributes().put("registration", new Boolean(true));
        } catch (DAOException e) {
            throw new ReceiverException("Problem when sign up Logic :", e);
        }
        helper.endTransaction();
    }

    @Override
    public boolean addToBasket(RequestContent content) throws ReceiverException {
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);

        String dishId = content.getRequestParameters().get(PARAM_DISH)[0];
        Dish dish = null;

        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(dishDao);

        try {
            dish = dishDao.findDishById(Integer.parseInt(dishId));
        } catch (DAOException e) {
            throw new ReceiverException("Problem when add to basket logic :", e);
        }
        helper.endTransaction();

        return user.addToBasket(dish);
    }

    @Override
    public boolean removeFromBasket(RequestContent content) throws ReceiverException {
        User user = (User)content.getSessionAttributes().get(SessionAtr.USER);

        String dishId = content.getRequestParameters().get(PARAM_DISH)[0];
        Dish dish = null;

        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(dishDao);

        try {
            dish = dishDao.findDishById(Integer.parseInt(dishId));
        } catch (DAOException e) {
            throw new ReceiverException("Problem when remove from basket logic :", e);
        }
        helper.endTransaction();

        return user.removeFromBasket(dish);
    }

    @Override
    public List<User> findUserBySurname(RequestContent content) throws ReceiverException {
        List<User> users = null;

        String surname = content.getRequestParameters().get(PARAM_SURNAME)[0];

        TransactionHelper helper = new TransactionHelper();
        UserDaoImpl userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        try {
            if (surname.equals(PARAM_ALL_USERS)){
                users = userDao.findAllUsers();
            } else {
                users = userDao.findUserBySurname(surname);
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem when find user by surname or find all users:", e);
        }
        helper.endTransaction();

        return users;
    }


}
