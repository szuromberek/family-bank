package com.epam.training.familybank.services;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.User;

public class AccountService {
    private final JpaAccountDao jpaAccountDao;
    
    @Resource
    private PlatformTransactionManager txManager;

    public AccountService(JpaAccountDao jpaAccountDao) {
        this.jpaAccountDao = jpaAccountDao;
    }

    public void sendGift(User fromUser, User toUser, BigDecimal amount) {
        Account fromAccount = getCurrentAccount(fromUser);
        Account toAccount = getCurrentAccount(toUser);
        transferFundsBetweenAccounts(toAccount, fromAccount, amount);
    }

    public void putMoneyInBank(User user, BigDecimal amount) {
        increaseBalanceByAmount(getCurrentAccount(user), amount);
    }
    
    public void takeMoneyOutOfBank(User user, BigDecimal amount) {
        decreaseBalanceByAmount(getCurrentAccount(user), amount);
    }
    
    public void getLoan(User user, BigDecimal amount) {
        if(sufficientBankFundsAvailable(amount)) {
            increaseBalanceByAmount(getCreditAccount(user), amount);
        } else {
            throw new InsufficientFundsException("Insufficient bank funds for transaction.");
        }
    }
    
    public void repayLoan(User user, BigDecimal amount) {
        decreaseBalanceByAmount(getCreditAccount(user), amount);
    }
    
    public void increaseSavings(User user, BigDecimal amount) {
        Account currentAccount = getCurrentAccount(user);
        Account savingsAccount = getSavingsAccount(user);
        transferFundsBetweenAccounts(currentAccount, savingsAccount, amount);
    }
    
    public void decreaseSavings(User user, BigDecimal amount) {
        Account currentAccount = getCurrentAccount(user);
        Account savingsAccount = getSavingsAccount(user);
        transferFundsBetweenAccounts(savingsAccount, currentAccount, amount);
    }

    private void transferFundsBetweenAccounts(Account fromAccount, Account toAccount, BigDecimal amount) {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        decreaseBalanceByAmount(fromAccount, amount);
        increaseBalanceByAmount(toAccount, amount);
        txManager.commit(status);
    }
    
    private void increaseBalanceByAmount(Account account, BigDecimal amount) {
        jpaAccountDao.updateBalance(account, account.getBalance().add(amount));
    }
    
    private void decreaseBalanceByAmount(Account account, BigDecimal amount) {
        if(account.getBalance().compareTo(amount) >= 0) {
            jpaAccountDao.updateBalance(account, account.getBalance().subtract(amount));
        } else {
            throw new InsufficientFundsException("Insufficient account balance for transaction.");
        }
    }
    
    private boolean sufficientBankFundsAvailable(BigDecimal amount) {
        BigDecimal totalSavedAmount = jpaAccountDao.queryTotalSavedAmount();
        BigDecimal totalLentAmount = jpaAccountDao.queryTotalLentAmount();
        BigDecimal borrowable = totalSavedAmount.subtract(totalLentAmount);
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
