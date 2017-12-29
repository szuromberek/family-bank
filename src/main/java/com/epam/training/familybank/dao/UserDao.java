package com.epam.training.familybank.dao;

import java.util.List;

import com.epam.training.familybank.domain.User;

public interface UserDao {
    void save(User user);
    List<User> queryAllUsers();
}
