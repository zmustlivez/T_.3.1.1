package com.example.dao;

import com.example.model.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user);
    User getUserByName(String name);
    List<User> getUsers();
    void updateUser(String name, String password);
    void deleteUser(String name);
}
