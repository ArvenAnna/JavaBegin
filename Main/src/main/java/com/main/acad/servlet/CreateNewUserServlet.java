package com.main.acad.servlet;

import com.main.acad.dao.UserDao;
import com.main.acad.dao.UserDaoMethods;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateNewUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDaoMethods userDao = new UserDao();
        boolean result = userDao.createNewUser(request.getParameter("login"), Integer.valueOf(request.getParameter("password"))
                , request.getParameter("role"));
        response.getWriter().write(String.valueOf(result));
    }
}
