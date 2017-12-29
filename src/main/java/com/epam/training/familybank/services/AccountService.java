package com.epam.training.familybank.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.User;

public class AccountService {
    public JpaAccountDao jpaAccountDao;
    @Resource
    public PlatformTransactionManager txManager;

    public AccountService(JpaAccountDao jpaAccountDao) {
        this.jpaAccountDao = jpaAccountDao;
    }
    
    public List<Account> listAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        List<Account> currentAccounts = jpaAccountDao.queryAccountsByType(AccountType.CURRENT);
        List<Account> creditAccounts = jpaAccountDao.queryAccountsByType(AccountType.CREDIT);
        List<Account> savingsAccounts = jpaAccountDao.queryAccountsByType(AccountType.SAVINGS);
        allAccounts.addAll(currentAccounts);
        allAccounts.addAll(creditAccounts);
        allAccounts.addAll(savingsAccounts);
        return allAccounts;
    }

    public void sendGift(User fromUser, User toUser, BigDecimal amount) {
        Account fromAccount = getCurrentAccount(fromUser);
        Account toAccount = getCurrentAccount(toUser);
        transferFundsBetweenAccounts(amount, fromAccount, toAccount);
    }

    public void putMoneyInBank(User user, BigDecimal amount) {
        depositFundsToAccount(amount, getCurrentAccount(user));
    }
    
    public void takeMoneyOutOfBank(User user, BigDecimal amount) {
        withdrawFundsFromAccount(amount, getCurrentAccount(user));
    }
    
    public void getLoan(User user, BigDecimal amount) {
        if(sufficientBankFundsAvailable(amount)) {
            depositFundsToAccount(amount, getCreditAccount(user));
        } else {
            throw new InsufficientFundsException("Insufficient bank funds for transaction.");
        }
    }
    
    public void repayLoan(User user, BigDecimal amount) {
        withdrawFundsFromAccount(amount, getCreditAccount(user));
    }
    
    public void increaseSavings(User user, BigDecimal amount) {
        Account currentAccount = getCurrentAccount(user);
        Account savingsAccount = getSavingsAccount(user);
        transferFundsBetweenAccounts(amount, currentAccount, savingsAccount);
    }
    
    public void decreaseSavings(User user, BigDecimal amount) {
        Account currentAccount = getCurrentAccount(user);
        Account savingsAccount = getSavingsAccount(user);
        transferFundsBetweenAccounts(amount, savingsAccount, currentAccount);
    }

    private void transferFundsBetweenAccounts(BigDecimal amount, Account fromAccount, Account toAccount) {
        withdrawFundsFromAccount(amount, fromAccount);
        depositFundsToAccount(amount, toAccount);
    }
    
    private void depositFundsToAccount(BigDecimal amount, Account account) {
        jpaAccountDao.updateBalance(account, account.getBalance().add(amount));
    }
    
    private void withdrawFundsFromAccount(BigDecimal amount, Account account) {
        if(account.getBalance().compareTo(amount) >= 0) {
            jpaAccountDao.updateBalance(account, account.getBalance().subtract(amount));
        } else {
            throw new InsufficientFundsException("Insufficient account balance for transaction.");
        }
    }
    
    private boolean sufficientBankFundsAvailable(BigDecimal amount) {
        BigDecimal allSavedAmount = jpaAccountDao.queryAmountAvailableToLend();
        BigDecimal allLentAmount = jpaAccountDao.queryAmountOnLoan();
        BigDecimal borrowable = allSavedAmount.subtract(allLentAmount);
        return borrowable.compareTo(amount) >= 0;
    }
    
    private Account getCurrentAccount(User user) {
        return jpaAccountDao.queryCurrentAccountByUser(user);
    }

    private Account getCreditAccount(User user) {
        return jpaAccountDao.queryCreditAccountByUser(user);
    }

    private Account getSavingsAccount(User user) {
        return jpaAccountDao.querySavingsAccountByUser(user);
    }

}
