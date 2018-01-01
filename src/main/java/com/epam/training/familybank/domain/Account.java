package com.epam.training.familybank.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance;
    Date interestCalculatedDate;


    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    @Override
    public String toString() {
        return "Account [id=" + id + ", user=" + user + ", accountType=" + accountType + ", balance=" + balance + ", interestCalculatedDate="
                + interestCalculatedDate + "]";
    }

}
