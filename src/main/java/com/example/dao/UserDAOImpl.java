package com.example.dao;

import com.example.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean addUser(User user) {
        try {
           getUserByName(user.getUsername());
            return false;
        } catch (NoResultException e) {
            entityManager.persist(user);
            return true;
        }
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
    public void updateUser(String username, String password) {
        User user =  entityManager.createQuery("from User where username =:username", User.class).setParameter("username", username).getSingleResult();
//        User user = getUserByName(password);
        user.setPassword(password);
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
//        entityManager.remove(entityManager.find(User.class, name));
//        entityManager.createQuery("delete from User where username=:name");
    }
}
