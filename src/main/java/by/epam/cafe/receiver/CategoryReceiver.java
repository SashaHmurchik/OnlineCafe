package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.exception.ReceiverException;

public interface CategoryReceiver extends Receiver {

    /**
     * Logic for add new category of dish in data base.
     * @param content - Content with category name parameter.
     * @return Execution result
     * @throws ReceiverException
     */
    boolean addNewCategory(RequestContent content) throws ReceiverException;

    /**
     * Logic for a category to the archive or returns from the archive
     * @param content With parameters category id and
     *                boolean parameter : true - in archive
     *                                    false - from archive
     * @return Execution result
     * @throws ReceiverException
     */
    boolean updateCategoryArchiveStatus(RequestContent content) throws ReceiverException;

    /**
     * Logic for add all dish categories in session
     * @param content content with session
     * @throws ReceiverException
     */
    void showCategory(RequestContent content) throws ReceiverException;
}
