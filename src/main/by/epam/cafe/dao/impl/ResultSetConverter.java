package by.epam.cafe.dao.impl;

import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.Category;
import by.epam.cafe.entity.Role;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ResultSetConverter {
    private static final String PASSPOET_PARAM = "passport";
    private static final String ID_PARAM = "id";
    private static final String LOYALTY_PARAM = "loyalty_points";
    private static final String ROLE_PARAM = "role";
    private static final String EMAIL_PARAM = "email";
    private static final String NAME_PARAM = "name";
    private static final String SURNAME_PARAM = "surname";
    private static final String PHONE_PARAM = "phone";
    private static final String PASSWORD_PARAM = "password";
    private static final String AVATAR_PARAM = "avatar";

    private static final String KITCHEN_NAME_PARAM = "name";
    private static final String KITCHEN_ID_PARAM = "id";
    private static final String KITCHEN_PHONE_PARAM = "phone";
    private static final String KITCHEN_ADDRESS_PARAM = "address";
    private static final String KITCHEN_EMAIL_PARAM = "email";
    private static final String KITCHEN_SITE_PARAM = "site";
    private static final String KITCHEN_PICTURE_PARAM = "picture";

    private static final String DISH_NAME_PARAM = "name";
    private static final String DISH_ID_PARAM = "id";
    private static final String DISH_KITCHEN_ID_PARAM = "kitchen_id";
    private static final String DISH_CATEGORY_ID_PARAM = "category_id";
    private static final String DISH_PRICE_PARAM = "price";
    private static final String DISH_DESCRIPTION_PARAM = "description";
    private static final String DISH_AMOUNT_PARAM = "amount";
    private static final String DISH_PICTURE_PARAM = "picture";

    private static final String CATEGORY_NAME_PARAM = "name";
    private static final String CATEGORY_ID_PARAM = "id";

    private static final String ORDER_ID_PARAM = "id";
    private static final String ORDER_USER_ID_PARAM = "user_id";
    private static final String ORDER_DATE_PARAM = "date";
    private static final String ORDER_DELIVERY_DATE_PARAM = "delivery_date";
    private static final String ORDER_PAID_PARAM = "paid";
    private static final String ORDER_PRICE_PARAM = "price";
    private static final String ORDER_DELIVERY_STATUS_PARAM = "delivery_status";

    private static final String ARCHIVE_PARAM = "archive";

    static User createUserEntity(ResultSet resultSet) throws DAOException {
        User user = null;
        try {
            Integer userId = resultSet.getInt(ID_PARAM);
            String email = resultSet.getString(EMAIL_PARAM);
            String password = resultSet.getString(PASSWORD_PARAM);
            String name = resultSet.getString(NAME_PARAM);
            String surname = resultSet.getString(SURNAME_PARAM);
            String phoneNumber = resultSet.getString(PHONE_PARAM);
            String role = resultSet.getString(ROLE_PARAM);
            String passport = resultSet.getString(PASSPOET_PARAM);
            String loyaltyPoints = resultSet.getString(LOYALTY_PARAM);
            String avatar = resultSet.getString(AVATAR_PARAM);

            user = new User();

            user.setLoyaltyPoint(Integer.parseInt(loyaltyPoints));
            user.setPassport(passport);
            user.setId(userId);
            user.setMail(email);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setPhone(phoneNumber);
            user.setRole(Role.valueOf(role.toUpperCase()));
            user.setAvatar(avatar);

        } catch (SQLException e) {
            throw new DAOException("Exception during creating User ", e);
        }
        return user;
    }

    static Kitchen createKitchenEntity(ResultSet resultSet) throws DAOException {
        Kitchen kitchen = null;
        try {
            Integer id = resultSet.getInt(KITCHEN_ID_PARAM);
            String name = resultSet.getString(KITCHEN_NAME_PARAM);
            String phone = resultSet.getString(KITCHEN_PHONE_PARAM);
            String address = resultSet.getString(KITCHEN_ADDRESS_PARAM);
            String site = resultSet.getString(KITCHEN_SITE_PARAM);
            String email = resultSet.getString(KITCHEN_EMAIL_PARAM);
            String picture = resultSet.getString(KITCHEN_PICTURE_PARAM);
            Boolean archiveStatus = resultSet.getBoolean(ARCHIVE_PARAM);

            kitchen = new Kitchen();

            kitchen.setAddress(address);
            kitchen.setId(id);
            kitchen.setName(name);
            kitchen.setEmail(email);
            kitchen.setPhone(phone);
            kitchen.setPicture(picture);
            kitchen.setSite(site);
            kitchen.setArchiveStatus(archiveStatus);

        } catch(SQLException e) {
            throw new DAOException("Exception during creating Kitchen ", e);
        }
        return kitchen;
    }

    static Dish createDishEntity(ResultSet resultSet) throws DAOException {
        Dish dish = null;
        try {
            Integer id = resultSet.getInt(DISH_ID_PARAM);
            String name = resultSet.getString(DISH_NAME_PARAM);
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(DISH_PRICE_PARAM));
            String description = resultSet.getString(DISH_DESCRIPTION_PARAM);
            String picture = resultSet.getString(DISH_PICTURE_PARAM);
            String amount = resultSet.getString(DISH_AMOUNT_PARAM);
            Boolean archiveStatus = resultSet.getBoolean(ARCHIVE_PARAM);

            Integer kitchenId = resultSet.getInt(DISH_KITCHEN_ID_PARAM);
            Integer categoryId = resultSet.getInt(DISH_CATEGORY_ID_PARAM);

            TransactionHelper helper = new TransactionHelper();
            KitchenDaoImpl kitchenDao = new KitchenDaoImpl();
            CategoryDaoImpl categoryDao = new CategoryDaoImpl();
            helper.beginTransaction(kitchenDao, categoryDao);

            Kitchen kitchen = kitchenDao.findKitchenById(kitchenId);
            Category category = categoryDao.findCategoryById(categoryId);

            helper.endTransaction();

            dish = new Dish();

            dish.setId(id);
            dish.setCategory(category);
            dish.setKitchen(kitchen);
            dish.setName(name);
            dish.setAmount(amount);
            dish.setDescription(description);
            dish.setPicture(picture);
            dish.setPrice(price);
            dish.setArchiveStatus(archiveStatus);

        } catch (SQLException e) {
            throw new DAOException("Exception during creating Dish", e);
        }
        return dish;
    }

    static Category createCategoryEntity(ResultSet resultSet) throws DAOException {
        Category category = null;
        try {
            Integer id = resultSet.getInt(CATEGORY_ID_PARAM);
            String name = resultSet.getString(CATEGORY_NAME_PARAM);
            Boolean archiveStatus = resultSet.getBoolean(ARCHIVE_PARAM);

            category = new Category();

            category.setId(id);
            category.setName(name);
            category.setArchiveStatus(archiveStatus);

        } catch (SQLException e) {
            throw new DAOException("Exception during creating Category", e);
        }
        return category;
    }

    static Order createOrderEntity(ResultSet resultSet, User user) throws DAOException {
        Order order = null;
        try {
            Integer id = resultSet.getInt(ORDER_ID_PARAM);
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(ORDER_PRICE_PARAM));
            Boolean paid = resultSet.getBoolean(ORDER_PAID_PARAM);
            LocalDate date = resultSet.getDate(ORDER_DATE_PARAM).toLocalDate();
            LocalDate deliveryDate = resultSet.getDate(ORDER_DELIVERY_DATE_PARAM).toLocalDate();
            Boolean deliveryStatus = resultSet.getBoolean(ORDER_DELIVERY_STATUS_PARAM);

            order = new Order();

            order.setId(id);
            order.setDate(date);
            order.setDeliveryDate(deliveryDate);
            order.setPrice(price);
            order.setUser(user);
            order.setPaid(paid);
            order.setDeliveryStatus(deliveryStatus);

        } catch (SQLException e) {
            throw new DAOException("Exception during creating Order", e);
        }
        return order;
    }

    static Order createOrderEntity(ResultSet resultSet) throws DAOException {
        Order order = null;
        try {
            Integer id = resultSet.getInt(ORDER_ID_PARAM);
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(ORDER_PRICE_PARAM));
            Boolean paid = resultSet.getBoolean(ORDER_PAID_PARAM);
            LocalDate date = resultSet.getDate(ORDER_DATE_PARAM).toLocalDate();
            LocalDate deliveryDate = resultSet.getDate(ORDER_DELIVERY_DATE_PARAM).toLocalDate();
            Integer userId = resultSet.getInt(ORDER_USER_ID_PARAM);
            Boolean deliveryStatus = resultSet.getBoolean(ORDER_DELIVERY_STATUS_PARAM);

            TransactionHelper helper = new TransactionHelper();
            UserDaoImpl userDao = new UserDaoImpl();
            helper.beginTransaction(userDao);

            User user = userDao.findUserById(userId);

            helper.endTransaction();

            order = new Order();

            order.setId(id);
            order.setDate(date);
            order.setDeliveryDate(deliveryDate);
            order.setPrice(price);
            order.setUser(user);
            order.setPaid(paid);
            order.setDeliveryStatus(deliveryStatus);

        } catch (SQLException e) {
            throw new DAOException("Exception during creating Order", e);
        }
        return order;
    }
}
