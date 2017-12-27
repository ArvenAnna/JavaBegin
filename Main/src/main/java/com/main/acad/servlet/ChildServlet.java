package com.main.acad.servlet;

import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChildServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Dao d = new ChapterDao();
        List child = null;
        try {
            child = d.listChildren(3);// як можна підставити значення того чи іншого заголовка??
                                          //все працює,тобто виводить childiv цього chaptera
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MyJsonSerializer m2 = new MyJsonSerializer();
        String res = "";
        try {
            res = m2.write(child);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        response.getWriter().write(res);

    }
}
