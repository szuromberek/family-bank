package com.epam.training.familybank;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class FamilyBankTest 
{
    private static Logger LOGGER = LoggerFactory.getLogger(FamilyBankTest.class);
    private AbstractApplicationContext context;
    private final AccountService accountService;
    private final InterestService interestService;
    private final UserService userService;
    AccountDao jpaAccountDao = new JpaAccountDao();
    
    public FamilyBankTest() {
        context = new AnnotationConfigApplicationContext(
                SpringConfigurationJpa.class, 
                SpringConfigurationDao.class,
                SpringConfigurationService.class);
        accountService = context.getBean(AccountService.class);
        interestService = context.getBean(InterestService.class);
        userService = context.getBean(UserService.class);
    }
    
    public static void main( String[] args ) throws ParseException
    {
        FamilyBankTest test = new FamilyBankTest();
        List<User> users = test.userService.getUsers();
        User user0 = users.get(0);
        User user1 = users.get(1);
        User user2 = users.get(2);
        
//        testPutMoneyInBank(test, user0, 500);
//        testPutMoneyInBank(test, user1, 500);
//        testPutMoneyInBank(test, user2, 500);
        
        //testSendGift(test, user0, user1, 100);
        
//        testTakeMoneyOutOfBank(test, user0, 1500);
//        testTakeMoneyOutOfBank(test, user1, 3900);
//        testTakeMoneyOutOfBank(test, user2, 4000);
        
        //testIncreaseSavings(test, user0, 100);
        //testIncreaseSavings(test, user1, 300);
        
          //testGetLoan(test, user2, 100);
        //testGetLoan(test, user2, 400);
        //testGetLoan(test, user2, 300);
        
        //testRepayLoan(test, user2, 50);

        //testDecreaseSavings(test, user0, 100);
        testAccountInterest(test);
    }

    private static void testAccountInterest(FamilyBankTest test) throws ParseException {
        test.interestService.accountCreditAndSavingsInterest();
        LOGGER.info("Interest accounted.");
    }

    private static void testDecreaseSavings(FamilyBankTest test, User user, int amount) {
        test.accountService.decreaseSavings(user, BigDecimal.valueOf(amount));
        LOGGER.info("{} decreased their savings by {} Galleons.", user, amount);
    }

    private static void testRepayLoan(FamilyBankTest test, User user, int amount) {
        test.accountService.repayLoan(user, BigDecimal.valueOf(amount));
        LOGGER.info("{} repayed {} Galleons of loan.", user, amount);
    }

    private static void testIncreaseSavings(FamilyBankTest test, User user, int amount) {
        test.accountService.increaseSavings(user, BigDecimal.valueOf(amount));
        LOGGER.info("{} saved {} Galleons.", user, amount);
    }

    private static void testGetLoan(FamilyBankTest test, User user, int amount) {
        test.accountService.getLoan(user, BigDecimal.valueOf(amount));
        LOGGER.info("{} borrowed {} Galleons.", user, amount);
    }

    private static void testTakeMoneyOutOfBank(FamilyBankTest test, User user, int amount) {
        test.accountService.takeMoneyOutOfBank(user, BigDecimal.valueOf(amount));
        LOGGER.info("{} withdrew {} Galleons.", user, amount);
    }

    private static void testPutMoneyInBank(FamilyBankTest test, User user, int amount) {
        test.accountService.putMoneyInBank(user, BigDecimal.valueOf(amount));
        LOGGER.info("{} deposited {} Galleons.", user, amount);
    }

    private static void testSendGift(FamilyBankTest test, User user1, User user2, int amount) {
        test.accountService.sendGift(user1, user2, BigDecimal.valueOf(amount));
        LOGGER.info("{} sent {} Galleons of gift to {}.", user1, amount, user2);
    }

}
