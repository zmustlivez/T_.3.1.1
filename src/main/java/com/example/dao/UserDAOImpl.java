package com.example.dao;

import com.example.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserByName(String name) {
        return entityManager.createQuery("from User where username=:name", User.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(String name, String password) {
        User user = getUserByName(name);
        user.setPassword(password);
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(String name) {

        entityManager.createQuery("delete from User where username=:name", User.class);
    }
}
