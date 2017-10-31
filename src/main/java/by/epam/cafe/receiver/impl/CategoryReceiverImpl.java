package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.CategoryDao;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.impl.CategoryDaoImpl;
import by.epam.cafe.dao.impl.DishDaoImpl;
import by.epam.cafe.dao.impl.KitchenDaoImpl;
import by.epam.cafe.entity.Category;
import by.epam.cafe.entity.Kitchen;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.CategoryReceiver;
import by.epam.cafe.resource.MessageManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryReceiverImpl implements CategoryReceiver {
    private static final String CHECKED_CATEGORY_ID_PARAM = "checkedCategoryId";
    private static final String ADD_CATEGORY_STATUS = "addCategoryStatus";
    private static final String CATEGORY_EXIST_MESSAGE = "message.category.exist";
    private static final String CATEGORY_LIST_PARAMETER = "categoryList";

    @Override
    public boolean addNewCategory(RequestContent content) throws ReceiverException {
        String categoryName = content.getRequestParameters().get(EntityAtr.CATEGORY_NAME)[0];

        boolean result = false;
        TransactionHelper helper = new TransactionHelper();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        helper.beginTransaction(categoryDao);
        try {
            if (categoryDao.findCategoryByName(categoryName) == null) {
                result = categoryDao.addNewCategory(categoryName);
            } else {
                String locale = (String)content.getSessionAttributes().get(SessionAtr.LOCALE);
                content.setRequestAttributes(ADD_CATEGORY_STATUS,
                        MessageManager.getManager(locale).getMessage(CATEGORY_EXIST_MESSAGE));
            }
        } catch (DAOException e) {
            throw new ReceiverException(e.getMessage(), e);
        }
        helper.endTransaction();
        return result;
    }

    @Override
    public boolean updateCategoryArchiveStatus(RequestContent content) throws ReceiverException {
        boolean result = false;
        String[] checkedCategoryId = content.getRequestParameters().get(CHECKED_CATEGORY_ID_PARAM);
        Boolean archiveStatus = Boolean.valueOf(content.getRequestParameters().get(EntityAtr.ARCHIVE_STATUS_PARAM)[0]);

        TransactionHelper helper = new TransactionHelper();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(categoryDao, dishDao);
        try {
            int res = checkedCategoryId.length;
            for (String s : checkedCategoryId) {
                Integer categoryId = Integer.valueOf(s);
                if(categoryDao.updateArchiveStatus(archiveStatus, categoryId)) {
                    if (archiveStatus) {
                        if (dishDao.updateArchiveStatusByCategoryId(archiveStatus, categoryId)) {
                            res -= 1;
                        }
                    } else {
                        res -= 1;
                    }
                }
            }
            if (res == 0){
                result = true;
            }
        } catch (DAOException e) {
            throw new ReceiverException("Problem during update category archive status logic", e);
        }
        helper.endTransaction();
        ArrayList<Category> categoryList = (ArrayList<Category>)content.getSessionAttributes().get(CATEGORY_LIST_PARAMETER);
        for (String s : checkedCategoryId) {
            for (Category c : categoryList) {
                if (c.getId().equals(Integer.valueOf(s))) {
                    categoryList.remove(c);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void showCategory(RequestContent content) throws ReceiverException {
        List<Category> categoryList = null;
        Boolean archiveStatus = false;

        if (content.getRequestParameters().get("kitchen") == null) {
            ReceiverHelper receiverHelper = new ReceiverHelper();
            archiveStatus = receiverHelper.getArchiveStatus(content);
        }

        TransactionHelper helper = new TransactionHelper();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        helper.beginTransaction(categoryDao);
        try {
            categoryList = categoryDao.findAllByArchiveStatus(archiveStatus);
        } catch (DAOException e) {
            throw new ReceiverException("Problem whith show category logic :", e);
        }
        helper.endTransaction();

        content.setSessionAttributes(CATEGORY_LIST_PARAMETER, categoryList);
    }
}
