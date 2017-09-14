package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.RequestParam;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.constant.SignUpAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.AbstractDAO;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.UserDao;
import by.epam.cafe.dao.impl.DishDaoImpl;
import by.epam.cafe.dao.impl.UserDaoImpl;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.pool.ProxyConnection;
import by.epam.cafe.receiver.UserReceiver;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;
import by.epam.cafe.util.HashPassword;
import by.epam.cafe.util.PasswordCreator;
import by.epam.cafe.util.Sender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class UserReceiverImpl implements UserReceiver {
    private static final String MAX_LOYALTY_POINT = ConfigurationManager.getProperty("max.loyalty.point");

    private static final String PARAM_USER_ID = "user_id";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_DISH = "dish";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_ALL_USERS = "allusers";
    private static final String MESSAGE_MAIL_SUBJECT_PARAM = "mail.message.subject";
    private static final String MESSAGE_MAIL_TEXT_PARAM = "mail.message.text";
    private static final String PARAM_CHANGE_LOYALTY_POINT_STATUS = "loyalty_status";
    private static final String POSITIV_LOYALTY_STATUS = "+";

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

    @Override
    public boolean editUser(RequestContent content) throws ReceiverException {
        boolean result = false;

        TransactionHelper helper = new TransactionHelper();
        UserDaoImpl userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        try {
            result = userDao.updateUser(content);
        } catch (DAOException e ) {
            throw new ReceiverException("Problem when update user logic:", e);
        }
        helper.endTransaction();
        return result;
    }

    @Override
    public boolean sendPassword(RequestContent content) throws ReceiverException {
        boolean result = false;
        String password = null;
        String eMail = content.getRequestParameters().get(SignUpAtr.EMAIL_PARAM)[0];
        String phone = content.getRequestParameters().get(SignUpAtr.PHONE_PARAM)[0];
        TransactionHelper helper = new TransactionHelper();
        UserDaoImpl userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        try {
            if (userDao.findUserByEmail(eMail)) {
                String dataPhone = userDao.getPhoneByEmail(eMail);
                if (dataPhone != null && dataPhone.equals(phone)) {
                    password = PasswordCreator.getPassword();
                    userDao.updateUserPassword(eMail, HashPassword.md5Custom(password));
                    result = true;
                }
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem when update user password logic:", e);
        }
        helper.endTransaction();
        if (result) {
            Sender sender = new Sender();
            String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
            String subject = MessageManager.getManager(locale).getMessage(MESSAGE_MAIL_SUBJECT_PARAM);
            String text = MessageManager.getManager(locale).getMessage(MESSAGE_MAIL_TEXT_PARAM);
            sender.send(subject, text + " " + password, eMail);
        }
        return result;
    }

    @Override
    public boolean changePassword(User user, String newPassword) throws ReceiverException {
        boolean result = false;

        TransactionHelper helper = new TransactionHelper();
        UserDaoImpl userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        try {
            result = userDao.updateUserPassword(user.getMail(), HashPassword.md5Custom(newPassword));
        } catch (DAOException e) {
            throw new ReceiverException("Problem when update user password logic:", e);
        }
        helper.endTransaction();

        return result;
    }

    @Override
    public boolean updateLoyaltyPointByUserId(RequestContent content) throws ReceiverException {
        boolean result = false;

        Integer userId = Integer.valueOf(content.getRequestParameters().get(PARAM_USER_ID)[0]);
        String loyaltyStatus = content.getRequestParameters().get(PARAM_CHANGE_LOYALTY_POINT_STATUS)[0];
        User currentUserFromSession = null;

        TransactionHelper helper = new TransactionHelper();
        UserDaoImpl userDao = new UserDaoImpl();
        helper.beginTransaction(userDao);
        try {
            User user = userDao.findUserById(userId);
            Integer currentLoyaltyPoint = user.getLoyaltyPoint();
            if (loyaltyStatus.equals(POSITIV_LOYALTY_STATUS)) {
                if (currentLoyaltyPoint != Integer.parseInt(MAX_LOYALTY_POINT)) {
                    currentLoyaltyPoint += 1;
                } else {
                    //пользователь уже имеет максимальный статус
                    content.setSessionAttributes("", "");
                }
            } else {
                if (currentLoyaltyPoint != 0) {
                    currentLoyaltyPoint -= 1;
                } else {
                    //пользователь уже заблокирован
                    content.setSessionAttributes("", "");
                }
            }
            helper.endTransaction();

            if (!content.getUrl().equals("http://localhost:8080/jsp/admin/admincort.jsp")) {
                Map<Order, HashMap<Dish, Integer>> orderMap =
                        (Map<Order, HashMap<Dish, Integer>>)content.getSessionAttributes().get(SessionAtr.ORDER_MAP);
                Set<Order> orderSet = orderMap.keySet();
                List<User> userList = new ArrayList<>();
                orderSet.stream().filter(o -> o.getUser().equals(user)).forEach(o -> userList.add(o.getUser()));
                currentUserFromSession = userList.get(0);
            } else {
                List<User> userList = (List<User>)content.getSessionAttributes().get(SessionAtr.USER_LIST);
                for (User u : userList) {
                    if (u.equals(user)) {
                        currentUserFromSession = u;
                        break;
                    }
                }
            }

            result = userDao.updateLoyaltyPointByUserId(currentLoyaltyPoint, userId);
            currentUserFromSession.setLoyaltyPoint(currentLoyaltyPoint);

        } catch (DAOException e) {
            throw new ReceiverException("Problem during update loyalty points logic", e);
        }
        return result;
    }
}
