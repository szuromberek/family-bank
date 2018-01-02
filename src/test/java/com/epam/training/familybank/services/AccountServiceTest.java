package com.epam.training.familybank.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epam.training.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.User;

public class AccountServiceTest {
    private JpaAccountDao mockJpaAccountDao;
    private AccountService underTest;
    
    @Before
    public void setUp() {
        mockJpaAccountDao = mock(JpaAccountDao.class);
        underTest = new AccountService(mockJpaAccountDao);
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testMockCreation(){
        assertNotNull(mockJpaAccountDao);
    }
    
    @Test
    public void testUpdateBalanceIsCalledOnFromAndToAccountWhenSendGiftCalled() {
        // Given
        Account mockFromAccount = mock(Account.class);
        Account mockToAccount = mock(Account.class);
        User mockFromUser = mock(User.class);
        User mockToUser = mock(User.class);
        BigDecimal amountToSend = BigDecimal.valueOf(100);
        BigDecimal fromUserBalance = BigDecimal.valueOf(200);
        BigDecimal toUserBalance = BigDecimal.ZERO;
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockFromUser)).thenReturn(mockFromAccount);
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockToUser)).thenReturn(mockToAccount);
        when(mockFromAccount.getBalance()).thenReturn(fromUserBalance);
        when(mockToAccount.getBalance()).thenReturn(toUserBalance);
        
        // When
        underTest.sendGift(mockFromUser, mockToUser, amountToSend);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockFromAccount, amountToSend);
        verify(mockJpaAccountDao).updateBalance(mockToAccount, amountToSend);
    }
    
    @Test
    public void testInsufficientFundsExceptionThrownWhenSendGiftCalled() {
        // Given
        Account mockFromAccount = mock(Account.class);
        Account mockToAccount = mock(Account.class);
        User mockFromUser = mock(User.class);
        User mockToUser = mock(User.class);
        BigDecimal amountToSend = BigDecimal.valueOf(500);
        BigDecimal firstUserBalance = BigDecimal.valueOf(100);
        BigDecimal secondUserBalance = BigDecimal.ZERO;
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockFromUser)).thenReturn(mockFromAccount);
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockToUser)).thenReturn(mockToAccount);
        when(mockFromAccount.getBalance()).thenReturn(firstUserBalance);
        when(mockToAccount.getBalance()).thenReturn(secondUserBalance);
        try {
        // When
            underTest.sendGift(mockFromUser, mockToUser, amountToSend);
        // Then
            fail();
        } catch (InsufficientFundsException expected) {
            assertEquals("Insufficient account balance for transaction.", expected.getMessage());
        }
    }
    
    @Test
    public void testUpdateBalanceIsCalledWhenPutMoneyInBankCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amount = BigDecimal.valueOf(500);
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(BigDecimal.ZERO);
        
        // When
        underTest.putMoneyInBank(mockUser, amount);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockAccount, amount);
    }
    
    @Test
    public void testUpdateBalanceIsCalledWhenTakeMoneyOutOfBankCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(500);
        BigDecimal amountAvailable = BigDecimal.valueOf(500);
        BigDecimal newBalance = amountAvailable.subtract(amountToWithdraw);
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(amountAvailable);
        
        // When
        underTest.takeMoneyOutOfBank(mockUser, amountToWithdraw);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockAccount, newBalance);
    }
    
    @Test
    public void testInsufficientFundsExceptionThrownWhenTakeMoneyOutOfBankCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amountToWithdraw = BigDecimal.valueOf(500);
        BigDecimal amountAvailable = BigDecimal.ZERO;
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(amountAvailable);
        try {
        // When
            underTest.takeMoneyOutOfBank(mockUser, amountToWithdraw);
        // Then
            fail();
        } catch (InsufficientFundsException expected) {
            assertEquals("Insufficient account balance for transaction.", expected.getMessage());
        }
    }
    
    @Test
    public void testUpdateBalanceIsCalledWhenGetLoanCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amountToBorrow = BigDecimal.valueOf(500);
        BigDecimal totalSavedAmount = BigDecimal.valueOf(500);
        BigDecimal totalLentAmount = BigDecimal.ZERO;
        BigDecimal currentBalance = BigDecimal.ZERO;
        BigDecimal newBalance = (currentBalance.add(amountToBorrow));
        when(mockJpaAccountDao.queryCreditAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockJpaAccountDao.queryTotalSavedAmount()).thenReturn(totalSavedAmount);
        when(mockJpaAccountDao.queryTotalLentAmount()).thenReturn(totalLentAmount);
        when(mockAccount.getBalance()).thenReturn(currentBalance);
        
        // When
        underTest.getLoan(mockUser, amountToBorrow);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockAccount, newBalance);
    }
    
    @Test
    public void testInsufficientFundsExceptionThrownWhenGetLoanCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amountToBorrow = BigDecimal.valueOf(500);
        BigDecimal totalSavedAmount = BigDecimal.ZERO;
        BigDecimal totalLentAmount = BigDecimal.ZERO;
        BigDecimal currentBalance = BigDecimal.ZERO;
        when(mockJpaAccountDao.queryCreditAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockJpaAccountDao.queryTotalSavedAmount()).thenReturn(totalSavedAmount);
        when(mockJpaAccountDao.queryTotalLentAmount()).thenReturn(totalLentAmount);
        when(mockAccount.getBalance()).thenReturn(currentBalance);
        try {
        // When
            underTest.getLoan(mockUser, amountToBorrow);
        // Then
            fail();
        } catch (InsufficientFundsException expected) {
            assertEquals("Insufficient bank funds for transaction.", expected.getMessage());
        }
    }
    
    @Test
    public void testUpdateBalanceIsCalledWhenRepayLoanCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amountToRepay = BigDecimal.valueOf(100);
        BigDecimal currentBalance = BigDecimal.valueOf(500);
        BigDecimal newBalance = (currentBalance.subtract(amountToRepay));
        when(mockJpaAccountDao.queryCreditAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(currentBalance);
        
        // When
        underTest.repayLoan(mockUser, amountToRepay);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockAccount, newBalance);
    }
    
    @Test
    public void testInsufficientFundsExceptionThrownWhenRepayLoanCalled() {
        // Given
        Account mockAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal amountToRepay = BigDecimal.valueOf(500);
        BigDecimal currentBalance = BigDecimal.valueOf(400);
        when(mockJpaAccountDao.queryCreditAccountByUser(mockUser)).thenReturn(mockAccount);
        when(mockAccount.getBalance()).thenReturn(currentBalance);
        try {
        // When
            underTest.repayLoan(mockUser, amountToRepay);
        // Then
            fail();
        } catch (InsufficientFundsException expected) {
            assertEquals("Insufficient account balance for transaction.", expected.getMessage());
        }
    }
    
    @Test
    public void testUpdateBalanceIsCalledOnCurrentAndSavingsAccountWhenIncreaseSavingsCalled() {
        // Given
        Account mockCurrentAccount = mock(Account.class);
        Account mockSavingsAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal currentAccountBalance = BigDecimal.valueOf(500);
        BigDecimal savingsAccountBalance = BigDecimal.ZERO;
        BigDecimal amountToSave = BigDecimal.valueOf(100);
        BigDecimal newCurrentBalance = currentAccountBalance.subtract(amountToSave);
        BigDecimal newSavingsBalance = savingsAccountBalance.add(amountToSave);
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockUser)).thenReturn(mockCurrentAccount);
        when(mockJpaAccountDao.querySavingsAccountByUser(mockUser)).thenReturn(mockSavingsAccount);
        when(mockCurrentAccount.getBalance()).thenReturn(currentAccountBalance);
        when(mockSavingsAccount.getBalance()).thenReturn(savingsAccountBalance);
        
        // When
        underTest.increaseSavings(mockUser, amountToSave);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockCurrentAccount, newCurrentBalance);
        verify(mockJpaAccountDao).updateBalance(mockSavingsAccount, newSavingsBalance);
    }
    
    @Test
    public void testUpdateBalanceIsCalledOnCurrentAndSavingsAccountWhenDecreaseSavingsCalled() {
     // Given
        Account mockCurrentAccount = mock(Account.class);
        Account mockSavingsAccount = mock(Account.class);
        User mockUser = mock(User.class);
        BigDecimal currentAccountBalance = BigDecimal.ZERO;
        BigDecimal savingsAccountBalance = new BigDecimal(200);
        BigDecimal amountToDecreaseSavingsBy = new BigDecimal(100);
        BigDecimal newCurrentBalance = currentAccountBalance.add(amountToDecreaseSavingsBy);
        BigDecimal newSavingsBalance = savingsAccountBalance.subtract(amountToDecreaseSavingsBy);
        when(mockJpaAccountDao.queryCurrentAccountByUser(mockUser)).thenReturn(mockCurrentAccount);
        when(mockJpaAccountDao.querySavingsAccountByUser(mockUser)).thenReturn(mockSavingsAccount);
        when(mockCurrentAccount.getBalance()).thenReturn(currentAccountBalance);
        when(mockSavingsAccount.getBalance()).thenReturn(savingsAccountBalance);
        
        // When
        underTest.decreaseSavings(mockUser, amountToDecreaseSavingsBy);
        
        // Then
        verify(mockJpaAccountDao).updateBalance(mockCurrentAccount, newCurrentBalance);
        verify(mockJpaAccountDao).updateBalance(mockSavingsAccount, newSavingsBalance);
    }
}
