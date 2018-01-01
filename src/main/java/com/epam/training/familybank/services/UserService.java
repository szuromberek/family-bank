package com.epam.training.familybank.services;

import java.util.List;

import com.epam.training.familybank.dao.UserDao;
import com.epam.training.familybank.domain.User;

public class UserService {
    public UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public List<User> getUsers() {
        return userDao.queryAllUsers();
    }
}
