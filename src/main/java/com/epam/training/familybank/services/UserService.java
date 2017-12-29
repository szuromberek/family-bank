package com.epam.training.familybank.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;

import com.epam.training.familybank.dao.jpaimpl.JpaUserDao;
import com.epam.training.familybank.domain.User;

public class UserService {
    public JpaUserDao jpaUserDao;
    @Resource
    public PlatformTransactionManager txManager;

    public UserService(JpaUserDao jpaUserDao) {
        this.jpaUserDao = jpaUserDao;
    }
    
    public List<User> listUsers() {
        return jpaUserDao.queryAllUsers();
    }
}
