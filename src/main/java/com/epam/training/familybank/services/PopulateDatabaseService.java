package com.epam.training.familybank.services;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.AccountType;
import com.epam.training.familybank.domain.User;

public class PopulateDatabaseService {
    
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Resource
    private PlatformTransactionManager txManager;
    
    public void populateDatabase() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        User user1 = createUser("Newton", "Scamander");
        User user2 = createUser("Credence", "Barebone");
        User user3 = createUser("Porpetina", "Goldstein");
        Account current1 = createAccount(user1, AccountType.CURRENT, new BigDecimal("2000"));
        Account savings1 = createAccount(user1, AccountType.SAVINGS, BigDecimal.ZERO);
        Account credit1 = createAccount(user1, AccountType.CREDIT, BigDecimal.ZERO);
        Account current2 = createAccount(user2, AccountType.CURRENT, new BigDecimal("2000"));
        Account savings2 = createAccount(user2, AccountType.SAVINGS, BigDecimal.ZERO);
        Account credit2 = createAccount(user2, AccountType.CREDIT, BigDecimal.ZERO);
        Account current3 = createAccount(user3, AccountType.CURRENT, new BigDecimal("2000"));
        Account savings3 = createAccount(user3, AccountType.SAVINGS, BigDecimal.ZERO);
        Account credit3 = createAccount(user3, AccountType.CREDIT, BigDecimal.ZERO);
        txManager.commit(status);
    }

    private User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        entityManager.persist(user);
        return user;
    }
    
    private Account createAccount(User user, AccountType accountType, BigDecimal balance) {
        Account account = new Account(user, accountType);
        account.setBalance(balance);
        entityManager.persist(account);
        return account;
    }

}
