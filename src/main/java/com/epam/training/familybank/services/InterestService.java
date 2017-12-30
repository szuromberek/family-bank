package com.epam.training.familybank.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.InterestRate;

public class InterestService {
    public JpaAccountDao jpaAccountDao;
    
    @Resource
    private PlatformTransactionManager txManager;

    public InterestService(JpaAccountDao jpaAccountDao) {
        this.jpaAccountDao = jpaAccountDao;
    }
    
    public void accountCreditAndSavingsInterest() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        accountInterest(AccountType.SAVINGS);
        accountInterest(AccountType.CREDIT);
        txManager.commit(status);
    }

    private void accountInterest(AccountType accountType) {
        List<Account> accounts = jpaAccountDao.queryAccountsByType(accountType);
        for(Account account : accounts) {
            BigDecimal balance = account.getBalance();
            BigDecimal rate = accountType.getInterestRate().getValue();
            BigDecimal oneDayInterest = balance.multiply(rate);
            Date lastInterestCalculatedDate = account.getInterestCalculatedDate();
            long numberOfDays = calculateInterestPeriod(lastInterestCalculatedDate);
            if(numberOfDays > 0) {
                BigDecimal newBalance = balance.add(oneDayInterest.multiply(BigDecimal.valueOf(numberOfDays)));
                jpaAccountDao.updateBalance(account, newBalance);
                jpaAccountDao.updateInterestCalculatedDate(account, new Date());
            }
        }
    }
    
    private long calculateInterestPeriod(Date lastInterestCalculatedDate) {
        Date now = new Date();
        long diff = now.getTime() - lastInterestCalculatedDate.getTime();
        long numberOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return numberOfDays;
    }
}