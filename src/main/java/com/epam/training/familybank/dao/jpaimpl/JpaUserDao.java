package com.epam.training.familybank.dao.jpaimpl;

import java.util.List;

import com.epam.training.familybank.dao.UserDao;
import com.epam.training.familybank.domain.Account;
import com.epam.training.familybank.domain.User;

public class JpaUserDao extends GenericJpaDao implements UserDao {

    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> queryAllUsers() {
        List<User> users = entityManager
                .createQuery("select u from User u", User.class)
                .getResultList();
        return users;
    }

}
