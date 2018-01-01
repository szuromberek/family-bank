package com.epam.training.familybank.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;

public class InterestServiceTest {
    private JpaAccountDao mockJpaAccountDao;
    private InterestService underTest;
    
    @Before
    public void setUp() {
        mockJpaAccountDao = mock(JpaAccountDao.class);
        underTest = new InterestService(mockJpaAccountDao);
    }
    
    @Test
    public void testMockCreation(){
        assertNotNull(mockJpaAccountDao);
    }
    
    @Test
    public void testUpdateBalanceAndUpdateInterestCalculatedDateCalledWhenAccountCreditAndSavingsInterestCalled() {
        // Given
        Account mockCreditAccount = mock(Account.class);
        Account mockSavingsAccount = mock(Account.class);
        List<Account> creditAccounts = new ArrayList<>();
        List<Account> savingsAccounts = new ArrayList<>();
        creditAccounts.add(mockCreditAccount);
        savingsAccounts.add(mockSavingsAccount);
        BigDecimal creditBalance = BigDecimal.valueOf(100);
        BigDecimal savingsBalance = BigDecimal.valueOf(100);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date dateMinus1Day = cal.getTime();
        when(mockJpaAccountDao.queryAccountsByType(AccountType.CREDIT)).thenReturn(creditAccounts);
        when(mockJpaAccountDao.queryAccountsByType(AccountType.SAVINGS)).thenReturn(savingsAccounts);
        when(mockCreditAccount.getBalance()).thenReturn(creditBalance);
        when(mockSavingsAccount.getBalance()).thenReturn(savingsBalance);
        when(mockCreditAccount.getInterestCalculatedDate()).thenReturn(dateMinus1Day);
        when(mockSavingsAccount.getInterestCalculatedDate()).thenReturn(dateMinus1Day);
        
        // When
        underTest.accountCreditAndSavingsInterest();
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockCreditAccount, BigDecimal.valueOf(Mockito.anyDouble());
        verify(mockJpaAccountDao).updateBalance(mockSavingsAccount, BigDecimal.valueOf(Mockito.anyDouble()));
        verify(mockJpaAccountDao).updateInterestCalculatedDate(mockCreditAccount, new Date());
        verify(mockJpaAccountDao).updateInterestCalculatedDate(mockSavingsAccount, new Date());
    }
}
