package com.epam.training.familybank.dao.jpaimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.epam.training.familybank.dao.AccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;

public class JpaAccountDao extends GenericJpaDao implements AccountDao {

    public List<Account> queryAccountsByType(AccountType accountType) {
        List<Account> accounts = entityManager
                .createQuery("select a from Account a join fetch a.Id"
                        + "and a.accountType = :accountType", Account.class)
                .setParameter("accountType", accountType)
                .getResultList();
        return accounts;
    }
    
    public Account queryCurrentAccountByUserId(int userId) {
        return queryAccountByUserIdAndAccountType(userId, AccountType.CURRENT);
    }
    
    public Account queryCreditAccountByUserId(int userId) {
        return queryAccountByUserIdAndAccountType(userId, AccountType.CREDIT);
    }
    
    public Account querySavingsAccountByUserId(int userId) {
        return queryAccountByUserIdAndAccountType(userId, AccountType.SAVINGS);
    }

    private Account queryAccountByUserIdAndAccountType(int userId, AccountType accountType) {
        List<Account> accounts = entityManager
              .createQuery("select a from Account a join fetch a.Id"
                      + "where a.userId = :userId"
                      + "and a.accountType = :accountType", Account.class)
              .setParameter("userId", userId)
              .setParameter("accountType", accountType)
              .getResultList();
      return accounts.get(0);
    }

    public BigDecimal queryCurrentAccountBalanceByUserId(int userId) {
        List<Account> accounts = entityManager
                .createQuery("select a from Account a join fetch a.balance"
                        + "where a.userId = :userId"
                        + "and a.accountType = :accountType", Account.class)
                .setParameter("userId", userId)
                .setParameter("accountType", AccountType.CURRENT)
                .getResultList();
        return accounts.get(0).getBalance();
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
    
//    public List<BigDecimal> queryDebitBalances() {
//        List<BigDecimal> balances = entityManager
//                .createQuery("select a.balance from Account a where a.accountType = :accountType", BigDecimal.class)
//                .setParameter("accountType", AccountType.DEBIT)
//                .getResultList();
//        return balances;
//    }
//    
//    public List<BigDecimal> queryCreditBalances() {
//        List<BigDecimal> balances = entityManager
//                .createQuery("select a.balance from Account a where a.accountType = :accountType", BigDecimal.class)
//                .setParameter("accountType", AccountType.CREDIT)
//                .getResultList();
//        return balances;
//    }
    
    public void updateBalance(Account account, BigDecimal balance) {
        entityManager
            .createQuery("update Account a set a.balance = :balance where a.id = :accountId")
            .setParameter("balance", balance)
            .setParameter("accountId", account.getId())
            .executeUpdate();
    }

//    public Date queryInterestCalculatedDate(Account account) {
//        List<Account> accounts = entityManager
//                .createQuery("select a from Account a join fetch a.interestCalculatedDate where a.id = :accountId", Account.class)
//                .setParameter("accountId", account.getId())
//                .getResultList();
//        return accounts.get(0).getInterestCalculatedDate();
//    }

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
