package com.epam.training.familybank.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.InterestRate;

public class InterestService {
    public JpaAccountDao jpaAccountDao;
    @Resource
    public PlatformTransactionManager txManager;

    public InterestService(JpaAccountDao jpaAccountDao) {
        this.jpaAccountDao = jpaAccountDao;
    }
    
    public void accountCreditAndSavingsInterest() {
        accountInterest(AccountType.SAVINGS, AccountType.SAVINGS.getInterestRate());
        accountInterest(AccountType.CREDIT, AccountType.CREDIT.getInterestRate());
    }

    private void accountInterest(AccountType accountType, InterestRate interestRate) {
        List<Account> accounts = jpaAccountDao.queryAccountsByType(accountType);
        for(Account account : accounts) {
            BigDecimal balance = account.getBalance();
            BigDecimal rate = interestRate.getValue();
            BigDecimal oneDayInterest = balance.add(balance).multiply(rate);
            Date lastInterestCalculatedDate = account.getInterestCalculatedDate();
            BigDecimal numberOfDays = BigDecimal.valueOf(calculateInterestPeriod(lastInterestCalculatedDate));
            BigDecimal newBalance = oneDayInterest.multiply(numberOfDays); 
            jpaAccountDao.updateBalance(account, newBalance);
            jpaAccountDao.updateInterestCalculatedDate(account);
        }
    }
    
    private long calculateInterestPeriod(Date lastInterestCalculatedDate) {
        Date now = new Date();
        long diff = now.getTime() - lastInterestCalculatedDate.getTime();
        long numberOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return numberOfDays;
    }
}