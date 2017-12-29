package com.epam.training.familybank.dao.jpaimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericJpaDao {
    @PersistenceContext
    protected EntityManager entityManager;
}
