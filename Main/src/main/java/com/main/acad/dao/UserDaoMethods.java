package com.main.acad.dao;

import com.main.acad.entity.User;

import java.util.List;

public interface UserDaoMethods {
    public List<User> findAll();
    public User findByUser(String login, Integer password);
}
