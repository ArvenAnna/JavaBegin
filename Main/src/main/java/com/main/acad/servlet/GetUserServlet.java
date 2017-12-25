package com.main.acad.servlet;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetUserServlet")
public class GetUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userName = request.getParameter("userName").trim();

        String greetings = "Hello " + userName;

        response.setContentType("text/plain");
        response.getWriter().write(greetings);
    }

}