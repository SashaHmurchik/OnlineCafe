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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        /////////////////////
        if (!dishList.isEmpty()) {
            int noOfItemsPerPage = 5;
            Integer currentPage = Integer.parseInt(content.getRequestParameters().get("current_page")[0]);
            int noOfPages = dishList.size() / noOfItemsPerPage;
            if (dishList.size() % noOfItemsPerPage != 0) {
                noOfPages += 1;
            }
            List<Dish> currentDishList = new ArrayList<>();
            int end = 0;
            if (currentPage == noOfPages) {
                end = dishList.size();
            } else {
                end = currentPage * noOfItemsPerPage;
            }
            int start = end - noOfItemsPerPage;
            if (start < 0) {
                start = 0;
            }
            for (int i = start; i < end; i++) {
                currentDishList.add(dishList.get(i));
            }
            String url = "";
            Pattern pattern = Pattern.compile("command=showdish");
            Matcher matcher = pattern.matcher(content.getUrl());
            if (matcher.find()) {
                String[] splitedUrl = content.getUrl().split("&");
                for (int i = 0; i < splitedUrl.length-1; i++) {
                    url = url.concat(splitedUrl[i] + "&");
                }
            } else {
                url = "http://localhost:8080/controller?command=showdish&";
            }

            content.setRequestAttributes(DISH_LIST_PARAMETER, currentDishList);
            content.setRequestAttributes("noOfPages", noOfPages);
            content.setRequestAttributes("current_page", currentPage);
            content.setRequestAttributes("urlForPagination", url);
        } else {
            content.setRequestAttributes(DISH_LIST_PARAMETER, dishList);
        }

        ////////////////////
        //content.setRequestAttributes(DISH_LIST_PARAMETER, dishList);
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

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("command=showdish");
        Matcher matcher = pattern.matcher("http://localhost:8080/controller?command=showdish&");
        System.out.println(matcher.find());
    }
}
