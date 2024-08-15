package com.example.dao;

import com.example.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean addUser(User user) {
        if (getUserByName(user.getUsername()) == null) {
            entityManager.persist(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUserByName(String name) {
        try {
            return entityManager.createQuery("from User where username=:name", User.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }
}
