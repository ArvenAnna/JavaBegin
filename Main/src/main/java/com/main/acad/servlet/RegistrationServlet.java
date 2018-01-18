package com.main.acad.servlet;

import com.main.acad.dao.UserDao;
import com.main.acad.dao.UserDaoMethods;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        UserDaoMethods userDao = new UserDao();
        if (userDao.exitUser(req.getParameter("login"))) {
            try {
                resp.getWriter().write("this is free login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                resp.getWriter().write("this is login exist");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
