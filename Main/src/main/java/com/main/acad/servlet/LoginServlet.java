package com.main.acad.servlet;

import com.main.acad.dao.UserDao;
import com.main.acad.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        UserDao userDao = new UserDao();
        User user;
        if (userDao.findByUser(req.getParameter("login"), Integer.valueOf(req.getParameter("password"))) == null) {
            try {
                resp.getWriter().write("Your login or password have some error please write again");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            user = userDao.findByUser(req.getParameter("login"), Integer.valueOf(req.getParameter("password")));
            if (user.getRole().trim().equals("admin")) {
                try {
                    resp.getWriter().write("This is logged admin ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (user.getRole().trim().equals("user")) {
                try {
                    resp.getWriter().write("This is logged user ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}