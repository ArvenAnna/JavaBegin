package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.dao.SimpleUserDao;
import com.main.acad.dao.UserDao;
import com.main.acad.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class UsersConntroller {
    private static final Logger logger = Logger.getLogger(ChaptersController.class.getName());

    @MappingMethod(url = "api/createNewUser")
    public void createNewUser(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new SimpleUserDao();
        boolean result = userDao.createNewUser(request.getParameter("login"),
                Integer.valueOf(request.getParameter("password")),
                request.getParameter("role"));
        try {
            response.getWriter().write(String.valueOf(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @MappingMethod(url = "api/checkDateUser")
    public void checkDateUser(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new SimpleUserDao();
        if (userDao.existUser(request.getParameter("login"))) {
            try {
                response.getWriter().write("this is free login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("this is login exist");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @MappingMethod(url = "api/login")
    public void checkUser(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new SimpleUserDao();
        User user;
        try {
            if (userDao.findByUser(request.getParameter("login"), Integer.valueOf(request.getParameter("password"))) == null) {
                response.getWriter().write("Your login or password have some error please write again");
            } else {
                user = userDao.findByUser(request.getParameter("login"), Integer.valueOf(request.getParameter("password")));
                if (user.getRole().trim().equals("admin")) {
                    response.getWriter().write("This is logged admin ");
                } else if (user.getRole().trim().equals("user")) {
                    response.getWriter().write("This is logged user ");
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
