package com.main.acad.servlet;

import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.serializator.MyJsonSerializer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BDServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Dao dao = new ChapterDao();
        List chaptersList = null;
        try {
            chaptersList = dao.listChapters();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MyJsonSerializer m = new MyJsonSerializer();
        String result = "";
        try {
            result = m.write(chaptersList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        response.getWriter().write(result);
    }
}
