package com.epam.training.familybank.services;

import org.mockito.Mockito;

import com.epam.training.familybank.dao.AccountDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;

public class AccountServiceTest {
    public void testSendGift() {
        AccountDao accountDaoMock = Mockito.mock(AccountDao.class);
        int user1Id = 1;
        int user2Id = 2;
        Mockito.when(accountDaoMock.queryCurrentAccountByUserId(user1Id)).thenReturn(new Account(user1Id, AccountType.CURRENT));
        Mockito.when(accountDaoMock.queryCurrentAccountByUserId(user2Id)).thenReturn(new Account(user2Id, AccountType.CURRENT));
        Mockito.when(accountDaoMock.updateBalance(account, balance);
    }
}
