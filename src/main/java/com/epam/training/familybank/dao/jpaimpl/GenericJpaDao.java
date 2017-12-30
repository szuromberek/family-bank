package com.epam.training.familybank.dao.jpaimpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.PlatformTransactionManager;

public abstract class GenericJpaDao {
    @PersistenceContext
    protected EntityManager entityManager;
    
    @Resource
    protected PlatformTransactionManager txManager;
}
