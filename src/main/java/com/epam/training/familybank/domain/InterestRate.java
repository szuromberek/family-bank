package com.epam.training.familybank.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public enum InterestRate {
    CURRENT("0"),
    CREDIT("0.002"),
    SAVINGS("0.001");
    
    @Id
    @GeneratedValue
    private int id;
    private BigDecimal value;
    
    private InterestRate() {}
    
    private InterestRate(String value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }
}
