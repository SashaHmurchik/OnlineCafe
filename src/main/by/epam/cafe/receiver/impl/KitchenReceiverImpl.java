package by.epam.cafe.receiver.impl;

import by.epam.cafe.constant.EntityAtr;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.dao.TransactionHelper;
import by.epam.cafe.dao.impl.CategoryDaoImpl;
import by.epam.cafe.dao.impl.DishDaoImpl;
import by.epam.cafe.dao.impl.KitchenDaoImpl;
import by.epam.cafe.entity.*;
import by.epam.cafe.exception.DAOException;
import by.epam.cafe.exception.ReceiverException;
import by.epam.cafe.receiver.KitchenReceiver;
import by.epam.cafe.receiver.Validator;

import java.util.ArrayList;
import java.util.List;


public class KitchenReceiverImpl implements KitchenReceiver{
    private static final String CHECKED_KITCHEN_ID_PARAM = "checkedKitchenId";
    private static final String KITCHEN_LIST_PARAMETER = "kitchenList";

    @Override
    public boolean addKitchen(RequestContent content) throws ReceiverException {

        boolean result = false;
        TransactionHelper helper = new TransactionHelper();
        KitchenDaoImpl kitchenDao = new KitchenDaoImpl();
        helper.beginTransaction(kitchenDao);
        try {
            result = kitchenDao.addKitchen(content);
        } catch (DAOException e) {
            throw new ReceiverException(e.getMessage(), e);
        }
        helper.endTransaction();
        return result;
    }

    @Override
    public boolean updateKitchenArchiveStatus(RequestContent content) throws ReceiverException {
        boolean result = false;
        String[] checkedKitchenId = content.getRequestParameters().get(CHECKED_KITCHEN_ID_PARAM);
        Boolean archiveStatus = Boolean.valueOf(content.getRequestParameters().get(EntityAtr.ARCHIVE_STATUS_PARAM)[0]);

        TransactionHelper helper = new TransactionHelper();
        KitchenDaoImpl kitchenDao = new KitchenDaoImpl();
        DishDaoImpl dishDao = new DishDaoImpl();
        helper.beginTransaction(kitchenDao, dishDao);
        try {
            int res = checkedKitchenId.length;
            for (String s : checkedKitchenId) {
                Integer kitchenId = Integer.valueOf(s);
                if(kitchenDao.updateArchiveStatus(kitchenId, archiveStatus)) {
                    if (archiveStatus) {
                        if (dishDao.updateArchiveStatusByKitchenID(archiveStatus, kitchenId)) {
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
            throw new ReceiverException("Problem during update kitchen archive status logic", e);
        }
        helper.endTransaction();
        ArrayList<Kitchen> kitchenList = (ArrayList<Kitchen>)content.getSessionAttributes().get(KITCHEN_LIST_PARAMETER);
        for (String s : checkedKitchenId) {
            for (Kitchen c : kitchenList) {
                if (c.getId().equals(Integer.valueOf(s))) {
                    kitchenList.remove(c);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void showKitchen(RequestContent content) throws ReceiverException {
        List<Kitchen> kitchenList = null;
        Boolean archiveStatus = false;

        if (content.getRequestParameters().get("kitchen") == null) {
            ReceiverHelper receiverHelper = new ReceiverHelper();
            archiveStatus = receiverHelper.getArchiveStatus(content);
        }

        TransactionHelper helper = new TransactionHelper();
        KitchenDaoImpl kitchenDao = new KitchenDaoImpl();
        helper.beginTransaction(kitchenDao);
        try {
            kitchenList = kitchenDao.findAllByArchiveStatus(archiveStatus);
        } catch (DAOException e) {
            throw new ReceiverException("Problem whith show kitchens logic :", e);
        }
        helper.endTransaction();

        content.setSessionAttributes(KITCHEN_LIST_PARAMETER, kitchenList);
    }

    @Override
    public boolean updateKitchen(RequestContent content) throws ReceiverException {
        boolean result = false;
        Integer id = Integer.valueOf(content.getRequestParameters().get(EntityAtr.KITCHEN_ID_PARAM)[0]);
        String kitchenAddress = content.getRequestParameters().get(EntityAtr.KITHEN_ADDRESS_PARAM)[0];
        String kitchenEmail = content.getRequestParameters().get(EntityAtr.KITCHEN_EMAIL_PARAM)[0];
        String kitchenPhone = content.getRequestParameters().get(EntityAtr.KITCHEN_PHONE_PARAM)[0];
        String kitchenSite = content.getRequestParameters().get(EntityAtr.KITCHEN_SITE_PARAM)[0];
        Boolean archiveStatus = Boolean.valueOf(content.getRequestParameters().get(EntityAtr.ARCHIVE_STATUS_PARAM)[0]);
        Kitchen kitchen = new Kitchen();
        kitchen.setId(id);
        kitchen.setAddress(kitchenAddress);
        kitchen.setEmail(kitchenEmail);
        kitchen.setPhone(kitchenPhone);
        kitchen.setSite(kitchenSite);
        kitchen.setArchiveStatus(archiveStatus);

        TransactionHelper helper = new TransactionHelper();
        KitchenDaoImpl kitchenDao = new KitchenDaoImpl();
        helper.beginTransaction(kitchenDao);
        try {
            result = kitchenDao.updateKitchen(kitchen);
        } catch (DAOException e) {
            throw new ReceiverException("Problem during update kitchen.", e);
        }
        helper.endTransaction();
        return result;
    }
}
