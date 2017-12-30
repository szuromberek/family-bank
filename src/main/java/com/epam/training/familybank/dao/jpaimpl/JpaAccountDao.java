package com.epam.training.familybank.dao.jpaimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    
    public BigDecimal queryAllSavedAmount() {
        BigDecimal sum = (BigDecimal) entityManager
                .createQuery("select sum(a.balance) from Account a where a.accountType = :accountType")
                .setParameter("accountType", AccountType.SAVINGS)
                .getSingleResult();
        return sum;
    }
    
    public BigDecimal queryAllLentAmount() {
        BigDecimal sum = (BigDecimal) entityManager
                .createQuery("select sum(a.balance) from Account a where a.accountType = :accountType")
                .setParameter("accountType", AccountType.CREDIT)
                .getSingleResult();
        return sum;
    }
    
    public void updateBalance(Account account, BigDecimal balance) {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        entityManager
            .createQuery("update Account a set a.balance = :balance where a.id = :accountId")
            .setParameter("balance", balance)
            .setParameter("accountId", account.getId())
            .executeUpdate();
        txManager.commit(status);
    }

    public void updateInterestCalculatedDate(Account account, Date date) {
        entityManager
        .createQuery("update Account a set a.interestCalculatedDate = :now where a.id = :accountId")
        .setParameter("now", date)
        .setParameter("accountId", account.getId())
        .executeUpdate();
    }
    
    public void save(Account account) {
        entityManager.persist(account);                
    }

}
