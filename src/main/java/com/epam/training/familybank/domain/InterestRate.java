package com.epam.training.familybank.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public enum InterestRate {
    CURRENT("0"),
    CREDIT("0.002"),
    SAVINGS("0.001");
    
    private final BigDecimal value;
    
    private InterestRate(String value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }
}
