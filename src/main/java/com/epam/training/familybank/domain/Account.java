package com.epam.training.familybank.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private final int userId;
    private final AccountType accountType;
    private BigDecimal balance;
    Date interestCalculatedDate;

    public Account(int userId, AccountType accountType) {
        this.userId = userId;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
        this.interestCalculatedDate = new Date();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getInterestCalculatedDate() {
        return interestCalculatedDate;
    }

    public void setInterestCalculatedDate(Date interestCalculatedDate) {
        this.interestCalculatedDate = interestCalculatedDate;
    }
}
