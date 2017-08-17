package by.epam.cafe.dao;

import by.epam.cafe.content.RequestContent;
import by.epam.cafe.entity.Order;
import by.epam.cafe.entity.User;
import by.epam.cafe.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    boolean findUserByEmail(String eMail) throws DAOException;
    User findUserByEmailAndPassword(String eMail, String password) throws DAOException;
    void addUser(RequestContent content) throws DAOException;
    User findUserById(Integer id) throws DAOException;
    List<User> findUserBySurname(String surname) throws DAOException;
    List<User> findAllUsers() throws DAOException;
    User findUserByPassport(String passport) throws DAOException;
}
