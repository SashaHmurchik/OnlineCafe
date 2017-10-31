package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.exception.ReceiverException;

public interface KitchenReceiver extends Receiver {
    /**
     * Logic for add new kitchen in data base
     * @param content with parameters of kitchen
     * @return Execution result
     * @throws ReceiverException if fail occurs while invoke write operation
     */
    boolean addKitchen(RequestContent content) throws ReceiverException;

    /**
     * Logic for add a kitchen to the archive or returns from the archive
     * @param content With parameters dish id and
     *                boolean parameter : <code>true</code> - in archive
     *                                    <code>false</code> - from archive
     * @return Execution result
     * @throws ReceiverException if fail occurs while invoke update operation
     */
    boolean updateKitchenArchiveStatus(RequestContent content) throws ReceiverException;

    /**
     * Logic for add all kitchen in session
     * @param content content with session map
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    void showKitchen(RequestContent content) throws ReceiverException;

    /**
     * Logic for Update kitchen parameters like name, address etc
     * all parameters in content object
     * @param content content with parameters
     * @throws ReceiverException if fail occurs while invoke update operation
     */
    boolean updateKitchen(RequestContent content) throws ReceiverException;
}
