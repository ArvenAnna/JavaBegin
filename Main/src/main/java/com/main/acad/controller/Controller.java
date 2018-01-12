package com.main.acad.controller;

import com.main.acad.annotation.MappingMethod;
import com.main.acad.dao.ChapterDao;
import com.main.acad.dao.Dao;
import com.main.acad.entity.Chapter;
import com.main.acad.serializator.MyJsonSerializer;
import com.main.acad.service.Service;
import com.main.acad.service.ServiceImplement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    private Service serviceImplement = new ServiceImplement();

    public Controller() throws SQLException, ClassNotFoundException {
    }

    @MappingMethod(url = "api/chapter")
    public void returnChapters(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException, SQLException, InterruptedException {
        List chaptersList = serviceImplement.listChapters();
        MyJsonSerializer m = new MyJsonSerializer();
        String result = m.write(chaptersList);
        response.getWriter().write(result);
    }

    @MappingMethod(url = "api/subChapter")
    public void returnSubChapters(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException {
        List<Chapter> child = serviceImplement.listChildren(Integer.parseInt(request.getParameter("id")));
        MyJsonSerializer m2 = new MyJsonSerializer();
        String result = m2.write(child);
        response.getWriter().write(result);
    }

    @MappingMethod(url = "api/subChapterByName")
    public void returnSubChaptersById(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, IllegalAccessException, IOException, SQLException {
        String chapter = serviceImplement.getInformstioAboutChildren(request.getParameter("name"));
        response.getWriter().write(chapter);
    }
}
