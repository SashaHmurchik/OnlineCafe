package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.DishDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.impl.CategoryDaoImpl;
import by.epam.cafe.dao.impl.DishDaoImpl;
import by.epam.cafe.dao.impl.KitchenDaoImpl;
import by.epam.cafe.entity.*;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.CategoryReceiver;
import by.epam.cafe.receiver.DishReceiver;
import by.epam.cafe.receiver.KitchenReceiver;

import java.util.List;

public class DishReceiverImpl implements DishReceiver {
    private static final String KITCHEN_PARAMETER = "kitchen";
    private static final String CATEGORY_PARAMETER = "category";
    private static final String EMPTY_PARAM_REGEX = "[x]";
    private static final String DISH_LIST_PARAMETER = "dishList";

    private static final String CHECKED_DISH_ID_PARAM = "checkedDishId";



    @Override
    public void showDish(RequestContent content) throws ReceiverException {
        List<Dish> dishList = null;
        ReceiverHelper receiverHelper = new ReceiverHelper();

        Boolean archiveStatus = receiverHelper.getArchiveStatus(content);
        String categoryParam = null;
        String kitchenParam = null;

        KitchenReceiver kitchenReceiver = new KitchenReceiverImpl();
        CategoryReceiver categoryReceiver = new CategoryReceiverImpl();
        categoryReceiver.showCategory(content);
        kitchenReceiver.showKitchen(content);

        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(dishDao);

        if (content.getRequestParameters().get(KITCHEN_PARAMETER) != null
                && content.getRequestParameters().get(CATEGORY_PARAMETER) != null) {
            kitchenParam = content.getRequestParameters().get(KITCHEN_PARAMETER)[0];
            categoryParam = content.getRequestParameters().get(CATEGORY_PARAMETER)[0];
        }

        try {
            if ((kitchenParam == null && categoryParam == null) ||
                    (kitchenParam.matches(EMPTY_PARAM_REGEX) &&
                            categoryParam.matches(EMPTY_PARAM_REGEX))) {
                dishList = dishDao.findAllDishByArchiveStatus(archiveStatus);
            } else {
                if (kitchenParam.matches(EMPTY_PARAM_REGEX)) {
                    Category category = new Category();
                    category.setId(Integer.valueOf(categoryParam));
                    dishList = dishDao.findDishByCategory(category, archiveStatus);
                } else if (categoryParam.matches(EMPTY_PARAM_REGEX)) {
                    Kitchen kitchen = new Kitchen();
                    kitchen.setId(Integer.valueOf(kitchenParam));
                    dishList = dishDao.findDishByKitchen(kitchen, archiveStatus);
                } else {
                    Category category = new Category();
                    category.setId(Integer.valueOf(categoryParam));
                    Kitchen kitchen = new Kitchen();
                    kitchen.setId(Integer.valueOf(kitchenParam));
                    dishList = dishDao.findDishByCategoryAndKitchen(category, kitchen, archiveStatus);
                }
            }
            helper.endTransaction();
        } catch (DAOException e) {
            throw new ReceiverException("Problem when dish Logic :", e);
        }

        content.setRequestAttributes(DISH_LIST_PARAMETER, dishList);
    }

    @Override
    public boolean addDish(RequestContent content) throws ReceiverException {
        boolean result = false;
        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(dishDao);
        try {
            result = dishDao.addNewDish(content);
        } catch (DAOException e) {
            throw new ReceiverException("Problem during add new dish", e);
        }
        helper.endTransaction();
        return result;
    }

    @Override
    public boolean updateArchiveStatus(RequestContent content) throws ReceiverException {
        boolean result = false;
        String[] checkedDishId = content.getRequestParameters().get(CHECKED_DISH_ID_PARAM);
        Boolean archiveStatus = Boolean.valueOf(content.getRequestParameters().get(EntityAtr.ARCHIVE_STATUS_PARAM)[0]);

        TransactionHelper helper = new TransactionHelper();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(dishDao);
        try {
            int res = checkedDishId.length;
            for (String s : checkedDishId) {
                Integer dishId = Integer.valueOf(s);
                Dish dish = dishDao.findDishById(dishId);
                if (!dish.getKitchen().getArchiveStatus()) {
                    if (dishDao.updateArchiveStatusByDishId(archiveStatus, dishId)) {
                        res -= 1;
                    }
                }
            }
            if (res == 0){
                result = true;
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem during update dish archive status logic", e);
        }
        helper.endTransaction();
        return result;
    }
}
