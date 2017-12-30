package com.epam.training.familybank.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;

import com.epam.training.familybank.dao.jpaimpl.JpaUserDao;
import com.epam.training.familybank.domain.User;

public class UserService {
    public JpaUserDao jpaUserDao;

    public UserService(JpaUserDao jpaUserDao) {
        this.jpaUserDao = jpaUserDao;
    }
    
    public List<User> getUsers() {
        return jpaUserDao.queryAllUsers();
    }
}
