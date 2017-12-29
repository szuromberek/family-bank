package com.epam.training.familybank.domain;

public enum AccountType {
    CURRENT(InterestRate.CURRENT),
    SAVINGS(InterestRate.SAVINGS),
    CREDIT(InterestRate.CREDIT);
    
    private final InterestRate interestRate;

    private AccountType(InterestRate interestRate) {
        this.interestRate = interestRate;
    }

    public InterestRate getInterestRate() { 
        return interestRate;
    }
}
