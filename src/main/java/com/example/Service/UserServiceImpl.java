package com.example.Service;

import com.example.dao.UserDAO;
import com.example.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
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
    public void updateUser(String name, String password) {
        userDAO.updateUser(name, password);
    }

    @Override
    public void deleteUser(String name) {
        userDAO.deleteUser(name);
    }
}
