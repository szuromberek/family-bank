package com.epam.training.familybank.dao.jpaimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.epam.training.familybank.dao.AccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.User;

public class JpaAccountDao extends GenericJpaDao implements AccountDao {

    public List<Account> queryAccountsByType(AccountType accountType) {
        List<Account> accounts = entityManager
                .createQuery("select a from Account a where a.accountType = :accountType", Account.class)
                .setParameter("accountType", accountType)
                .getResultList();
        return accounts;
    }
    
    public Account queryCurrentAccountByUser(User user) {
        return queryAccountByUserIdAndAccountType(user, AccountType.CURRENT);
    }
    
    public Account queryCreditAccountByUser(User user) {
        return queryAccountByUserIdAndAccountType(user, AccountType.CREDIT);
    }
    
    public Account querySavingsAccountByUser(User user) {
        return queryAccountByUserIdAndAccountType(user, AccountType.SAVINGS);
    }

    private Account queryAccountByUserIdAndAccountType(User user, AccountType accountType) {
        List<Account> accounts = entityManager
              .createQuery("select a from Account a where a.user = :user and a.accountType = :accountType", Account.class)
              .setParameter("user", user)
              .setParameter("accountType", accountType)
              .getResultList();
      return accounts.get(0);
    }  
    
    public BigDecimal queryAmountAvailableToLend() {
        BigDecimal sum = (BigDecimal) entityManager
                .createQuery("select sum(a.balance) from Account a where a.accountType = :accountType")
                .setParameter("accountType", AccountType.SAVINGS)
                .getResultList();
        return sum;
    }
    
    public BigDecimal queryAmountOnLoan() {
        BigDecimal sum = (BigDecimal) entityManager
                .createQuery("select sum(a.balance) from Account a where a.accountType = :accountType")
                .setParameter("accountType", AccountType.CREDIT)
                .getResultList();
        return sum;
    }
    
    public void updateBalance(Account account, BigDecimal balance) {
        entityManager
            .createQuery("update Account a set a.balance = :balance where a.id = :accountId")
            .setParameter("balance", balance)
            .setParameter("accountId", account.getId())
            .executeUpdate();
    }

    public void updateInterestCalculatedDate(Account account) {
        entityManager
        .createQuery("update Account a set a.interestCalculatedDate = :now where a.id = :accountId")
        .setParameter("now", new Date())
        .setParameter("accountId", account.getId())
        .executeUpdate();
    }
    
    public void save(Account account) {
        entityManager.persist(account);                
    }

}
