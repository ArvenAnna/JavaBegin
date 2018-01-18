package com.main.acad.servlet;

import com.main.acad.dao.UserDao;
import com.main.acad.dao.UserDaoMethods;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserDaoMethods userDao = new UserDao();
        if (userDao.exitUser(req.getParameter("login"))) {
            resp.getWriter().write("this is free login");
        } else {
            resp.getWriter().write("this is login exist");
        }
    }
}
