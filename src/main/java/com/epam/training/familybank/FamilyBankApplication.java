package com.epam.training.familybank;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.epam.training.familybank.dao.AccountDao;
import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.User;
import com.epam.training.familybank.services.AccountService;
import com.epam.training.familybank.services.InterestService;
import com.epam.training.familybank.services.UserService;
import com.epam.training.familybank.spring.SpringConfigurationDao;
import com.epam.training.familybank.spring.SpringConfigurationJpa;
import com.epam.training.familybank.spring.SpringConfigurationService;

public class FamilyBankApplication 
{
    private static Logger LOGGER = LoggerFactory.getLogger(FamilyBankApplication.class);
    private AbstractApplicationContext context;
    private final AccountService accountService;
    private final InterestService interestService;
    private final UserService userService;
    AccountDao accountDao = new JpaAccountDao();
    
    public FamilyBankApplication() {
        context = new AnnotationConfigApplicationContext(
                SpringConfigurationJpa.class, 
                SpringConfigurationDao.class,
                SpringConfigurationService.class);
        accountService = context.getBean(AccountService.class);
        interestService = context.getBean(InterestService.class);
        userService = context.getBean(UserService.class);
    }
    
    public static void main( String[] args )
    {
        FamilyBankApplication test = new FamilyBankApplication();
        List<User> users = test.userService.listUsers();
        LOGGER.info("{}", users.toString());
        List<Account> accounts = test.accountService.listAllAccounts();
        LOGGER.info("{}", accounts.toString());
        User user0 = users.get(0);
        User user1 = users.get(1);
        test.accountService.sendGift(user0, user1, new BigDecimal(1000));
        List<Account> updatedAccounts = test.accountService.listAllAccounts();
        LOGGER.info("{}", updatedAccounts.toString());
    }

}
