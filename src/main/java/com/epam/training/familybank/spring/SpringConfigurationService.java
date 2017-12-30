package com.epam.training.familybank.spring;

import org.springframework.context.annotation.Bean;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.dao.jpaimpl.JpaUserDao;
import com.epam.training.familybank.services.AccountService;
import com.epam.training.familybank.services.InterestService;
import com.epam.training.familybank.services.PopulateDatabaseService;
import com.epam.training.familybank.services.UserService;

public class SpringConfigurationService {
    
    @Bean
    public AccountService accountService(JpaAccountDao jpaAccountDao) {
        return new AccountService(jpaAccountDao);
    }
    
    @Bean
    public InterestService interestService(JpaAccountDao jpaAccountDao) {
        return new InterestService(jpaAccountDao);
    }
    
    @Bean
    public UserService userService(JpaUserDao jpaUserDao) {
        return new UserService(jpaUserDao);
    }
    
    //@Bean(initMethod = "populateDatabase")
    public PopulateDatabaseService populateDatabaseService() {
        return new PopulateDatabaseService();
    }
}
