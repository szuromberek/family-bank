package com.epam.training.familybank.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.User;

public interface AccountDao {
    List<Account> queryAccountsByType(AccountType accountType);
    Account queryCurrentAccountByUser(User user);
    Account queryCreditAccountByUser(User user);
    Account querySavingsAccountByUser(User user);
    BigDecimal queryTotalSavedAmount();
    BigDecimal queryTotalLentAmount();
    void updateBalance(Account account, BigDecimal newBalance);
    public void updateInterestCalculatedDate(Account account, Date date);
    void save(Account account);
}
