package com.epam.training.familybank.dao;

import java.math.BigDecimal;
import java.util.List;

import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;

public interface AccountDao {
    List<Account> queryAccountsByType(AccountType accountType);
    Account queryCurrentAccountByUserId(int userId);
    Account queryCreditAccountByUserId(int userId);
    Account querySavingsAccountByUserId(int userId);
    BigDecimal queryAmountAvailableToLend();
    BigDecimal queryAmountOnLoan();
    void updateBalance(Account account, BigDecimal balance);
    public void updateInterestCalculatedDate(Account account);
    void save(Account account);
}
