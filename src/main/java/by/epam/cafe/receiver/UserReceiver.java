package by.epam.cafe.receiver;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.ReceiverException;

import java.util.ArrayList;
import java.util.List;

public interface UserReceiver extends Receiver {
    /**
     * Logic for login. Checks if there is such an email address in the database.
     * If exists, it checks if the password matches.
     * @param content with parameters eMail and password
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    void loginLogic(RequestContent content) throws ReceiverException;

    /**
     * Checks if there is such an email address in the database.
     * If exists, it add in request attribute marker about it.
     * @param content with parameters eMail.
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    boolean isEmailExist(RequestContent content) throws ReceiverException;

    /**
     * Logic for signUp. Checks if there is such an email address in the database.
     * If exists, it checks if the password matches.
     * @param content with parameters eMail and password
     * @throws ReceiverException if fail occurs while invoke write operation
     */
    void signUpLogic(RequestContent content) throws ReceiverException;

    /**
     * Logic for add dish in user basket. User object with basket is in session.
     * In this logic connection to data base for find dish by dish id
     * @param content with user object in session attribute and dish id
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    boolean  addToBasket(RequestContent content) throws ReceiverException;

    /**
     * Logic for remove dish from user basket. User object with basket is in session.
     * In this logic connection to data base for find dish by dish id.
     * @param content with user object in session attribute and dish id
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    boolean removeFromBasket(RequestContent content) throws ReceiverException;

    /**
     * Find user in data base.
     * @param content with user name or marker for find all users
     * @return List of users
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    List<User> findUserBySurname(RequestContent content) throws ReceiverException;

    /**
     * Edit user information in data base and session.
     * @param content with parameters for edit.
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException
     */
    boolean editUser(RequestContent content) throws ReceiverException;

    /**
     * Logic for new sending password on email if user forgot password.
     * This logic contains logic for check input parameters from user like
     * user email and user phone number. If checked logic returned false
     * user will be informed about it.
     * @param content with email and phone
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke read operation
     */
    boolean sendPassword(RequestContent content) throws ReceiverException;

    /**
     * Logic for change password.
     * @param user - attribute from session
     * @param newPassword - password for changing
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke write operation
     */
    boolean changePassword(User user, String newPassword) throws ReceiverException;

    /**
     * Logic for update user loyalty point.
     * @param content with parameter user id and change status
     *                <code>true</code> - update to positive side
     *                <code>false</code> - update to negative side
     *                if loyalty point is 0 - user blocked
     * @return Execution result :
     *              <code>true</code> - success
     *              <code>false</code> - not success
     * @throws ReceiverException if fail occurs while invoke write operation
     */
    boolean updateLoyaltyPointByUserId(RequestContent content) throws ReceiverException;
}
