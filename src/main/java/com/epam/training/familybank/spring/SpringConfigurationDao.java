package com.epam.training.familybank.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import com.epam.training.familybank.dao.AccountDao;
import com.epam.training.familybank.dao.UserDao;
import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.dao.jpaimpl.JpaUserDao;

public class SpringConfigurationDao {

    @Bean
    public AccountDao accountDao() {
        return new JpaAccountDao();
    }
    
    @Bean
    public UserDao userDao() {
        return new JpaUserDao();
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor petpp() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
}