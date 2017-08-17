package by.epam.cafe.receiver.impl;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.CategoryDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.impl.CategoryDaoImpl;
import by.epam.cafe.dao.impl.DishDaoImpl;
import by.epam.cafe.dao.impl.KitchenDaoImpl;
import by.epam.cafe.entity.Dish;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.entity.charackter.Category;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.DishReceiver;

import java.util.List;

public class DishReceiverImpl implements DishReceiver {
    private static final String KITCHEN_PARAMETER = "kitchen";
    private static final String CATEGORY_PARAMENER = "category";
    private static final String EMPTY_PARAM_REGEX = "[x]";
    private static final String DISH_LIST_PARAMETER = "dishList";
    private static final String CATEGORY_LIST_PARAMEER = "categoryList";
    private static final String KITCHEN_LIST_PARAMETER = "kitchenList";

    @Override
    public void showDish(RequestContent content) throws ReceiverException {
        List<Dish> dishList = null;
        List<Category> categoryList = null;
        List<Kitchen>  kitchenList = null;

        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dishDao = new DishDaoImpl();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        KitchenDaoImpl kitchenDao = new KitchenDaoImpl();
        helper.beginTransaction(dishDao, categoryDao, kitchenDao);

        String categoryParam = null;
        String kitchenPatam = null;

        if (content.getRequestParameters().get(KITCHEN_PARAMETER) != null
                && content.getRequestParameters().get(CATEGORY_PARAMENER) != null) {
            kitchenPatam = content.getRequestParameters().get(KITCHEN_PARAMETER)[0];
            categoryParam = content.getRequestParameters().get(CATEGORY_PARAMENER)[0];
        }

        try {
            categoryList = categoryDao.findAll();
            kitchenList = kitchenDao.findAll();

            if ((kitchenPatam == null && categoryParam == null) ||
                    (kitchenPatam.matches(EMPTY_PARAM_REGEX) &&
                            categoryParam.matches(EMPTY_PARAM_REGEX))) {
                dishList = dishDao.findAll();
            } else {
                if (kitchenPatam.matches(EMPTY_PARAM_REGEX)) {
                    Category category = new Category();
                    category.setId(Integer.valueOf(categoryParam));
                    dishList = dishDao.findDishByCategory(category);
                } else if (categoryParam.matches(EMPTY_PARAM_REGEX)) {
                    Kitchen kitchen = new Kitchen();
                    kitchen.setId(Integer.valueOf(kitchenPatam));
                    dishList = dishDao.findDishByKitchen(kitchen);
                } else {
                    Category category = new Category();
                    category.setId(Integer.valueOf(categoryParam));
                    Kitchen kitchen = new Kitchen();
                    kitchen.setId(Integer.valueOf(kitchenPatam));
                    dishList = dishDao.findDishByCategoryAndKitchen(category, kitchen);
                }
            }
            helper.endTransaction();
        } catch (DAOException e) {
            throw new ReceiverException("Problem when dish Logic :", e);
        }

        content.setRequestAttributes(DISH_LIST_PARAMETER, dishList);
        content.setSessionAttributes(CATEGORY_LIST_PARAMEER, categoryList);
        content.setSessionAttributes(KITCHEN_LIST_PARAMETER, kitchenList);
    }
}
