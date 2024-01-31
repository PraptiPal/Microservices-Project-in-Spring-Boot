package com.micro.userservice.services;

import com.micro.userservice.entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    //User updateUser(User user, Integer userID);

    public User getUserById(String userID);

    public List<User> getAllUsers();

    //void deleteUser(Integer userId);
}
