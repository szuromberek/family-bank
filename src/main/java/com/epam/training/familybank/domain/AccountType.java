package com.epam.training.familybank.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public enum AccountType {
    CURRENT(InterestRate.CURRENT),
    SAVINGS(InterestRate.SAVINGS),
    CREDIT(InterestRate.CREDIT);
    
    @Id
    @GeneratedValue
    private int id;
    
    @Enumerated
    private InterestRate interestRate;
    
    private AccountType() {}

    private AccountType(InterestRate interestRate) {
        this.interestRate = interestRate;
    }

    public InterestRate getInterestRate() { 
        return interestRate;
    }
}
