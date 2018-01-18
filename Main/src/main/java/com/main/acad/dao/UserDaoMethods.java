package com.main.acad.dao;

import com.main.acad.entity.User;

import java.util.List;

public interface UserDaoMethods {
    List<User> findAll();

    User findByUser(String login, Integer password);

    boolean exitUser(String userLogin);

    boolean createNewUser(String login, Integer password, String role);
}
