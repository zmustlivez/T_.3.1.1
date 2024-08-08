package com.example.service;

import com.example.model.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    User getUserByName(String name);

    List<User> getUsers();

    void updateUser(User user);

    void deleteUser(String name);
}
