package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }


    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();

    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);

    }

    @Override
    @Transactional
    public void deleteUser(String name) {
        userDAO.deleteUser(getUserByName(name));

    }
}
