package com.epam.training.familybank.spring;

import org.springframework.context.annotation.Bean;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.services.AccountService;
import com.epam.training.familybank.services.PopulateDatabaseService;

public class SpringConfigurationService {
    
    @Bean
    public AccountService accountService(JpaAccountDao jpaAccountDao) {
        return new AccountService(jpaAccountDao);
    }
    
    @Bean(initMethod = "populateDatabase")
    public PopulateDatabaseService populateDatabaseService() {
        return new PopulateDatabaseService();
    }
}
