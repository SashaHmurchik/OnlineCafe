package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.exception.ReceiverException;

public interface DishReceiver extends Receiver {

    /**
     * Logic for add all Dish in request attributes
     * @param content content with request map
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    void showDish(RequestContent content) throws ReceiverException;

    /**
     * Logic for add new dish in data base
     * @param content with parameters:
     *     name of dish, price of dish, category id of,
     *                kitchen id of dish and etc
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code>  - not success
     * @throws ReceiverException if fail occurs while invoke write operation
     */
    boolean addDish(RequestContent content) throws ReceiverException;

    /**
     * Logic for add a dish to the archive or returns from the archive
     * @param content With parameters dish id and
     *                boolean parameter : true - in archive
     *                                    false - from archive
     * @return Execution result
     * @throws ReceiverException if fail occurs while invoke update operation
     */
    boolean updateArchiveStatus(RequestContent content) throws ReceiverException;
}
