package com.main.acad.dao;

import com.main.acad.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findByUser(String login, Integer password);

    boolean existUser(String userLogin);

    boolean createNewUser(String login, Integer password, String role);
}
